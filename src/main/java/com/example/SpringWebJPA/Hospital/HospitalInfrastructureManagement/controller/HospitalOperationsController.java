package com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.controller;

import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.dto.EmergencySosDTO;
import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.dto.SosDispatchReceiptDTO;
import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.model.*;
import com.example.SpringWebJPA.Hospital.HospitalInfrastructureManagement.service.HospitalOperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital/operations")
@RequiredArgsConstructor
public class HospitalOperationsController {

    private final HospitalOperationsService operationsService;

    @PostMapping("/admin/setup-blood")
    public ResponseEntity<BloodInventory> addBlood(@RequestParam String bloodType, @RequestParam Integer units) {
        return ResponseEntity.ok(operationsService.updateBloodStock(bloodType, units));
    }

    @PostMapping("/admin/add-ambulance")
    public ResponseEntity<AmbulanceTracker> addAmbulance(@RequestBody AmbulanceTracker vehicle) {
        return ResponseEntity.ok(operationsService.registerAmbulance(vehicle));
    }

    @PostMapping("/admin/add-bed")
    public ResponseEntity<ErBedTracker> addBed(@RequestBody ErBedTracker bed) {
        return ResponseEntity.ok(operationsService.registerErBed(bed));
    }

    // Incoming Distress Gateway Integration for patients
    @PostMapping("/patient/sos-trigger")
    public ResponseEntity<SosDispatchReceiptDTO> triggerEmergencySosAlert(@RequestBody EmergencySosDTO request) {
        return ResponseEntity.ok(operationsService.processEmergencySosTrigger(request));
    }
}
