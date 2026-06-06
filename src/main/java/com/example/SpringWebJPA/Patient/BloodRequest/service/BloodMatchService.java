package com.example.SpringWebJPA.Patient.BloodRequest.service;

import com.example.SpringWebJPA.Patient.BloodRequest.dto.BloodRequestDTO;
import com.example.SpringWebJPA.Patient.BloodRequest.dto.DonorMatchDTO;
import com.example.SpringWebJPA.Patient.BloodRequest.model.BloodDonor;
import com.example.SpringWebJPA.Patient.BloodRequest.model.BloodRequest;
import com.example.SpringWebJPA.Patient.BloodRequest.repository.BloodDonorRepository;
import com.example.SpringWebJPA.Patient.BloodRequest.repository.BloodRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BloodMatchService {
    private final BloodRequestRepository requestRepository;
    private final BloodDonorRepository donorRepository;

    @Transactional
    public BloodRequest submitBloodRequest(BloodRequestDTO dto, MultipartFile receiptFile) throws IOException {
        String uploadDir = "C:/intellicare/receipts/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileId = UUID.randomUUID() + "_" + receiptFile.getOriginalFilename();
        File targetFile = new File(dir, fileId);
        receiptFile.transferTo(targetFile);

        BloodRequest request = BloodRequest.builder()
                .patientId(dto.getPatientId())
                .bloodType(dto.getBloodType())
                .unitsRequested(dto.getUnitsRequested())
                .receiptStorageUrl(targetFile.getAbsolutePath())
                .isReceiptVerified(true) // Automatically trusted receipt verification for testing flow
                .build();

        return requestRepository.save(request);
    }

    public List<DonorMatchDTO> getEligibleDonorsForPatient(Long patientId) {
        // Enforce Rule: Only fetch donor profiles if a verified blood request transaction exists for the patient
        BloodRequest currentRequest = requestRepository.findFirstByPatientIdOrderByCreatedAtDesc(patientId)
                .orElseThrow(() -> new SecurityException("Access Denied: No blood request record found. Please upload a receipt first."));

        if (!currentRequest.isReceiptVerified()) {
            throw new SecurityException("Access Denied: Submitted request receipt validation is still pending.");
        }

        List<BloodDonor> compatibleDonors = donorRepository.findCompatibleDonors(currentRequest.getBloodType());

        return compatibleDonors.stream()
                .map(donor -> DonorMatchDTO.builder()
                        .donorId(donor.getId())
                        .fullName(donor.getFullName())
                        .bloodType(donor.getBloodType())
                        .contactNumber(donor.getContactNumber())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String confirmDonation(Long requestId) {
        BloodRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Blood request reference not found."));

        request.setStatus("FULFILLED");
        requestRepository.save(request);
        return "Donation confirmed. Transaction closed.";
    }
}
