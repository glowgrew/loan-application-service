package org.example.loanapplicationservice;

import org.example.loanapplicationservice.dto.ApplicationResultDTO;
import org.example.loanapplicationservice.dto.LoanTermsDTO;
import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.repository.ApplicationRepository;
import org.example.loanapplicationservice.service.CreditScoreService;
import org.example.loanapplicationservice.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application(1L, "Test User", "test@example.com", new BigDecimal("10000"), "PENDING");
    }

    @Test
    void processApplication_ShouldReturnApproved() {
        ApplicationResultDTO result = new ApplicationResultDTO(true, "Approved by default");
        when(applicationRepository.findById(any(Long.class))).thenReturn(Optional.of(application));

        ApplicationResultDTO response = applicationService.processApplication(application);

        assertTrue(response.approved());
        verify(applicationRepository, times(1)).save(any(Application.class));
    }

    @Test
    void validateApplication_ShouldReturnTrue() {
        boolean isValid = applicationService.validateApplication(application);

        assertTrue(isValid);
        verify(applicationRepository, never()).save(any(Application.class));
    }

    @Test
    void updateStatus_ShouldReturnTrue() {
        Long id = 1L;
        String status = "APPROVED";
        when(applicationRepository.updateStatus(id, status)).thenReturn(1);

        boolean updated = applicationService.updateStatus(id, status);

        assertTrue(updated);
        verify(applicationRepository, times(1)).updateStatus(id, status);
    }

    @Test
    void calculateLoanTerms_ShouldReturnValidLoanTerms() {
        when(applicationRepository.findById(any(Long.class))).thenReturn(Optional.of(application));

        LoanTermsDTO terms = applicationService.calculateLoanTerms(application);

        assertEquals(new BigDecimal("5.0"), terms.interestRate());
        assertEquals(24, terms.loanDurationMonths());
        verify(applicationRepository, never()).save(any(Application.class));
    }
}
