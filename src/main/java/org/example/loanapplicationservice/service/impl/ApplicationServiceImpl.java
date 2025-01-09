package org.example.loanapplicationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.loanapplicationservice.dto.ApplicationResultDTO;
import org.example.loanapplicationservice.dto.LoanTermsDTO;
import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.repository.ApplicationRepository;
import org.example.loanapplicationservice.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public ApplicationResultDTO processApplication(Application application) {
        application = applicationRepository.findById(application.getId()).orElseThrow();
        application.setStatus("APPROVED");
        applicationRepository.save(application);
        return new ApplicationResultDTO(true, "Approved by default");
    }

    @Override
    public boolean validateApplication(Application application) {
        return application.getRequestedAmount().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean updateStatus(Long id, String status) {
        return applicationRepository.updateStatus(id, status) > 0;
    }

    @Override
    public LoanTermsDTO calculateLoanTerms(Application application) {
        application = applicationRepository.findById(application.getId()).orElseThrow();
        return new LoanTermsDTO(new BigDecimal("5.0"), 24);
    }

}
