package com.example.SpringWebJPA.Patient.EmergencySOS.service;

import com.example.SpringWebJPA.Patient.EmergencySOS.dto.SosResponseDTO;
import com.example.SpringWebJPA.Patient.EmergencySOS.dto.SosTriggerRequest;
import com.example.SpringWebJPA.Patient.EmergencySOS.model.Ambulance;
import com.example.SpringWebJPA.Patient.EmergencySOS.model.EmergencyIncident;
import com.example.SpringWebJPA.Patient.EmergencySOS.model.HospitalER;
import com.example.SpringWebJPA.Patient.EmergencySOS.repository.AmbulanceRepository;
import com.example.SpringWebJPA.Patient.EmergencySOS.repository.EmergencyIncidentRepository;
import com.example.SpringWebJPA.Patient.EmergencySOS.repository.HospitalERRepository;
import com.example.SpringWebJPA.Patient.EmergencySOS.model.*;
import com.example.SpringWebJPA.Patient.EmergencySOS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SosDispatchService {
    private final EmergencyIncidentRepository incidentRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final HospitalERRepository hospitalRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public SosResponseDTO executeOneTapSos(SosTriggerRequest request) {
        // 1. Initialize Incident
        EmergencyIncident incident = EmergencyIncident.builder()
                .patientId(request.getPatientId())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
        incidentRepository.save(incident);

        // 2. Locate Nearest Available Ambulance using Haversine approximation
        List<Ambulance> availableAmbulances = ambulanceRepository.findAvailableAmbulances();
        Ambulance closestAmbulance = null;
        double minAmbulanceDistance = Double.MAX_VALUE;

        for (Ambulance amp : availableAmbulances) {
            double distance = calculateDistance(request.getLatitude(), request.getLongitude(), 
                    amp.getCurrentLatitude(), amp.getCurrentLongitude());
            if (distance < minAmbulanceDistance) {
                minAmbulanceDistance = distance;
                closestAmbulance = amp;
            }
        }

        if (closestAmbulance != null) {
            closestAmbulance.setAvailable(false);
            ambulanceRepository.save(closestAmbulance);
            incident.setAssignedAmbulanceId(closestAmbulance.getId());
            incident.setStatus("AMBULANCE_DISPATCHED");
        }

        // 3. Find Nearest Hospital ER Node for Pre-Alert
        List<HospitalER> hospitals = hospitalRepository.findAll();
        HospitalER nearestHospital = null;
        double minHospitalDistance = Double.MAX_VALUE;

        for (HospitalER hosp : hospitals) {
            double distance = calculateDistance(request.getLatitude(), request.getLongitude(), 
                    hosp.getLatitude(), hosp.getLongitude());
            if (distance < minHospitalDistance) {
                minHospitalDistance = distance;
                nearestHospital = hosp;
            }
        }

        if (nearestHospital != null) {
            incident.setTargetHospitalId(nearestHospital.getId());
            // Fire asynchronous pre-alert telemetry system package to target ER
            triggerHospitalPreAlert(nearestHospital, incident);
        }

        incidentRepository.save(incident);

        return SosResponseDTO.builder()
                .incidentId(incident.getId())
                .status(incident.getStatus())
                .assignedAmbulanceVehicle(closestAmbulance != null ? closestAmbulance.getVehicleNumber() : "NONE_AVAILABLE")
                .ambulanceLatitude(closestAmbulance != null ? closestAmbulance.getCurrentLatitude() : null)
                .ambulanceLongitude(closestAmbulance != null ? closestAmbulance.getCurrentLongitude() : null)
                .alertedHospitalName(nearestHospital != null ? nearestHospital.getHospitalName() : "NONE_IN_RANGE")
                .build();
    }

    private void triggerHospitalPreAlert(HospitalER hospital, EmergencyIncident incident) {
        try {
            String alertPayload = String.format(
                "{\"alertType\":\"CRITICAL_SOS\",\"incidentId\":%d,\"estimatedArrival\":\"12 minutes\"}", 
                incident.getId()
            );
            // Simulating API integration call out to the Hospital ER dashboard system
            restTemplate.postForLocation(hospital.getEndpointUrl(), alertPayload);
        } catch (Exception e) {
            // Log warning but prevent network exceptions from stopping the primary dispatch execution loop
            System.err.println("Hospital network pre-alert delivery bypassed: " + e.getMessage());
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + 
                      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist * 60 * 1.1515 * 1.609344; // Kilometer extraction
    }
}