package org.example.loanapplicationservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.loanapplicationservice.dto.ApplicationDTO;
import org.example.loanapplicationservice.dto.ApplicationRequestDTO;
import org.example.loanapplicationservice.dto.ApplicationResultDTO;
import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.builder.ApplicationBuilder;
import org.example.loanapplicationservice.repository.ApplicationRepository;
import org.example.loanapplicationservice.repository.ApplicationRepositoryImpl;
import org.example.loanapplicationservice.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationRequestDTO request) {
        Application application = new ApplicationBuilder()
            .withName(request.name())
            .withEmail(request.email())
            .withRequestedAmount(request.requestedAmount())
            .withStatus("PENDING")
            .build();

        ApplicationResultDTO result = applicationService.processApplication(application);

        ApplicationDTO response = new ApplicationDTO(1L,
            application.getName(),
            application.getEmail(),
            application.getRequestedAmount(),
            result.approved() ? "APPROVED" : "REJECTED");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long id) {
        Application application = applicationRepository.findById(id).orElseThrow();
        ApplicationDTO response = new ApplicationDTO(id,
            application.getName(),
            application.getEmail(),
            application.getRequestedAmount(),
            application.getStatus());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateApplicationStatus(@PathVariable Long id, @RequestParam String status) {
        boolean updated = applicationService.updateStatus(id, status);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
