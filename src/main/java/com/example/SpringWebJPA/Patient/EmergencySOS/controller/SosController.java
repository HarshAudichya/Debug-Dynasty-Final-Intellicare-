package com.example.SpringWebJPA.Patient.EmergencySOS.controller;

import com.example.SpringWebJPA.Patient.EmergencySOS.dto.SosResponseDTO;
import com.example.SpringWebJPA.Patient.EmergencySOS.dto.SosTriggerRequest;
import com.example.SpringWebJPA.Patient.EmergencySOS.service.SosDispatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emergency/sos")
@RequiredArgsConstructor
public class SosController {
    private final SosDispatchService sosService;

    @PostMapping("/trigger")
    public ResponseEntity<SosResponseDTO> triggerOneTapEmergency(@RequestBody SosTriggerRequest request) {
        return ResponseEntity.ok(sosService.executeOneTapSos(request));
    }
}