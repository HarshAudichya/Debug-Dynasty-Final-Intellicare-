package com.example.SpringWebJPA.Patient.BloodRequest.controller;

import com.example.SpringWebJPA.Patient.BloodRequest.dto.BloodRequestDTO;
import com.example.SpringWebJPA.Patient.BloodRequest.dto.DonorMatchDTO;
import com.example.SpringWebJPA.Patient.BloodRequest.model.BloodRequest;
import com.example.SpringWebJPA.Patient.BloodRequest.service.BloodMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/blood")
@RequiredArgsConstructor
public class BloodController {
    private final BloodMatchService bloodService;

    @PostMapping(value = "/request", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BloodRequest> createBloodRequest(
            @RequestPart("data") BloodRequestDTO data,
            @RequestPart("receipt") MultipartFile receipt) throws IOException {
        return ResponseEntity.ok(bloodService.submitBloodRequest(data, receipt));
    }

    @GetMapping("/donors-list")
    public ResponseEntity<List<DonorMatchDTO>> viewMatchedDonors(@RequestParam("patientId") Long patientId) {
        return ResponseEntity.ok(bloodService.getEligibleDonorsForPatient(patientId));
    }

    @PutMapping("/confirm-fulfillment")
    public ResponseEntity<String> completeDonationRoute(@RequestParam("requestId") Long requestId) {
        return ResponseEntity.ok(bloodService.confirmDonation(requestId));
    }
}
