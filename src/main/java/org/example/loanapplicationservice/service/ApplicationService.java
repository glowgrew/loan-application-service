package org.example.loanapplicationservice.service;

import org.example.loanapplicationservice.dto.ApplicationResultDTO;
import org.example.loanapplicationservice.dto.LoanTermsDTO;
import org.example.loanapplicationservice.entity.Application;

public interface ApplicationService {

    ApplicationResultDTO processApplication(Application application);

    boolean validateApplication(Application application);

    boolean updateStatus(Long id, String status);

    LoanTermsDTO calculateLoanTerms(Application application);

}
