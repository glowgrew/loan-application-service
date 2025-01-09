package org.example.loanapplicationservice.service;

import org.example.loanapplicationservice.entity.Application;

public interface CreditScoreService {

    int calculateCreditScore(Application application);

}
