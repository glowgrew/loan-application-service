package org.example.loanapplicationservice.decorator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.loanapplicationservice.dto.ApplicationResultDTO;
import org.example.loanapplicationservice.dto.LoanTermsDTO;
import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.service.ApplicationService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingApplicationService implements ApplicationService {

    private final ApplicationService applicationService;
    
    @Override
    public ApplicationResultDTO processApplication(Application application) {
        log.info("Processing application for user: {}", application.getName());
        try {
            ApplicationResultDTO result = applicationService.processApplication(application);
            log.info("Application processed successfully. Result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error processing application: {}", e.getMessage());
            throw e;
        }
    }
    
    @Override
    public boolean validateApplication(Application application) {
        log.info("Validating application for user: {}", application.getName());
        boolean result = applicationService.validateApplication(application);
        log.info("Validation result: {}", result);
        return result;
    }
    
    @Override
    public boolean updateStatus(Long id, String status) {
        log.info("Updating status for application: {} to {}", id, status);
        boolean result = applicationService.updateStatus(id, status);
        log.info("Status update result: {}", result);
        return result;
    }
    
    @Override
    public LoanTermsDTO calculateLoanTerms(Application application) {
        log.info("Calculating loan terms for application: {}", application.getId());
        LoanTermsDTO terms = applicationService.calculateLoanTerms(application);
        log.info("Calculated loan terms: {}", terms);
        return terms;
    }
}

