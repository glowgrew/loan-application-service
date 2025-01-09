package org.example.loanapplicationservice;

import org.example.loanapplicationservice.controller.ApplicationController;
import org.example.loanapplicationservice.dto.ApplicationDTO;
import org.example.loanapplicationservice.dto.ApplicationRequestDTO;
import org.example.loanapplicationservice.dto.ApplicationResultDTO;
import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.repository.ApplicationRepository;
import org.example.loanapplicationservice.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationControllerTest {

    @Mock
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationController applicationController;

    private ApplicationRequestDTO applicationRequestDTO;

    @BeforeEach
    void setUp() {
        applicationRequestDTO = new ApplicationRequestDTO("Test User", "test@example.com", new BigDecimal("10000"));
    }

    @Test
    void createApplication_ShouldReturnApproved() {
        Application application = new Application(null,
            "Test User",
            "test@example.com",
            new BigDecimal("10000"),
            "PENDING");
        ApplicationResultDTO result = new ApplicationResultDTO(true, "Approved by default");
        when(applicationService.processApplication(any(Application.class))).thenReturn(result);
        when(applicationRepository.findById(any(Long.class))).thenReturn(Optional.of(application));

        ResponseEntity<ApplicationDTO> response = applicationController.createApplication(applicationRequestDTO);

        assertNotNull(response);
        assertEquals("APPROVED", response.getBody().status());
        verify(applicationService, times(1)).processApplication(any(Application.class));
    }

    @Test
    void getApplicationById_ShouldReturnApplication() {
        Long applicationId = 1L;
        when(applicationRepository.findById(any(Long.class))).thenReturn(Optional.of(new Application(applicationId,
            "Test application",
            "test@mail.ru",
            BigDecimal.TEN,
            "PENDING")));
        when(applicationService.processApplication(any(Application.class))).thenReturn(new ApplicationResultDTO(true,
            "Approved by default"));

        ResponseEntity<ApplicationDTO> response = applicationController.getApplicationById(applicationId);

        assertNotNull(response);
        assertEquals("Test application", response.getBody().name());
        verify(applicationService, never()).processApplication(any(Application.class));
    }

    @Test
    void updateApplicationStatus_ShouldReturnOk() {
        Long applicationId = 1L;
        String status = "APPROVED";
        when(applicationService.updateStatus(applicationId, status)).thenReturn(true);

        ResponseEntity<Void> response = applicationController.updateApplicationStatus(applicationId, status);

        assertEquals(200, response.getStatusCodeValue());
        verify(applicationService, times(1)).updateStatus(applicationId, status);
    }

    @Test
    void updateApplicationStatus_ShouldReturnBadRequest() {
        Long applicationId = 1L;
        String status = "REJECTED";
        when(applicationService.updateStatus(applicationId, status)).thenReturn(false);

        ResponseEntity<Void> response = applicationController.updateApplicationStatus(applicationId, status);

        assertEquals(400, response.getStatusCodeValue());
        verify(applicationService, times(1)).updateStatus(applicationId, status);
    }
}
