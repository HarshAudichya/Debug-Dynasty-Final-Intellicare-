package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.service;

import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.dto.EmergencySosDTO;
import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.dto.SosDispatchReceiptDTO;
import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model.*;
import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HospitalOperationsService {

    private final BloodInventoryRepository bloodRepository;
    private final AmbulanceTrackerRepository ambulanceRepository;
    private final ErBedTrackerRepository bedRepository;

    // Infrastructure manual entry setups
    @Transactional
    public BloodInventory updateBloodStock(String type, Integer units) {
        BloodInventory blood = bloodRepository.findByBloodType(type)
                .orElse(BloodInventory.builder().bloodType(type).unitsAvailable(0).build());
        blood.setUnitsAvailable(blood.getUnitsAvailable() + units);
        return bloodRepository.save(blood);
    }

    @Transactional
    public AmbulanceTracker registerAmbulance(AmbulanceTracker vehicle) {
        return ambulanceRepository.save(vehicle);
    }

    @Transactional
    public ErBedTracker registerErBed(ErBedTracker bed) {
        return bedRepository.save(bed);
    }

    // CRITICAL CORE LOGIC: Unified Emergency SOS Automation Chain Hub
    @Transactional
    public SosDispatchReceiptDTO processEmergencySosTrigger(EmergencySosDTO dto) {

        // 1. Dispatch earliest available physical ambulance vehicle
        var freeAmbulances = ambulanceRepository.findByOperationalStatus("AVAILABLE");
        AmbulanceTracker dispatchedAmbulance = null;
        if (!freeAmbulances.isEmpty()) {
            dispatchedAmbulance = freeAmbulances.get(0);
            dispatchedAmbulance.setOperationalStatus("EN_ROUTE_TO_PATIENT");
            ambulanceRepository.save(dispatchedAmbulance);
        }

        // 2. Locate and allocate an empty physical ER trauma bed room block
        var freeBeds = bedRepository.findByIsOccupiedFalse();
        ErBedTracker reservedBed = null;
        if (!freeBeds.isEmpty()) {
            reservedBed = freeBeds.get(0);
            reservedBed.setOccupied(true);
            reservedBed.setAssignedPatientId(dto.getPatientId());
            bedRepository.save(reservedBed);
        }

        // 3. Real-time Blood Bank verification crosscheck scan
        var bloodNode = bloodRepository.findByBloodType(dto.getPatientBloodType());
        String bloodNotice = "BLOOD LEVEL WARNING: Requested type " + dto.getPatientBloodType() + " matching units drop below safe bank parameters!";
        if (bloodNode.isPresent() && bloodNode.get().getUnitsAvailable() > 5) {
            bloodNotice = "BLOOD READY: Safe reserves exist for type " + dto.getPatientBloodType() + " (" + bloodNode.get().getUnitsAvailable() + " units on hand).";
        }

        // 4. Console log output alerts for hospital dashboards
        System.out.println("=== EMERGENCY DISPATCH INBOUND ALERT ===");
        System.out.println("PATIENT ID: " + dto.getPatientId() + " triggered SOS at coordinates: " + dto.getCurrentGpsCoordinates());
        System.out.println("VEHICLE OUT BOUND: " + (dispatchedAmbulance != null ? dispatchedAmbulance.getVehicleNumber() : "ALL VEHICLES BUSY - QUEUED"));
        System.out.println("ER ROOM RESERVED: " + (reservedBed != null ? reservedBed.getBedCode() : "TRAUMA WARD CAPACITY FULL - ALERT ON-CALL MANAGER"));
        System.out.println("========================================");

        return SosDispatchReceiptDTO.builder()
                .status("DISPATCHED_CRITICAL")
                .assignedAmbulanceVehicle(dispatchedAmbulance != null ? dispatchedAmbulance.getVehicleNumber() : "NONE_AVAILABLE_IN_FLUID_POOL")
                .reservedErBedCode(reservedBed != null ? reservedBed.getBedCode() : "WARD_CAPACITY_MAXED")
                .bloodBankStatusNotice(bloodNotice)
                .dispatchLogMessage("Distress beacons intercepted. Trauma resources routed to coordinates successfully.")
                .build();
    }
}