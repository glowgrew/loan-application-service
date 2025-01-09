package org.example.loanapplicationservice.service.impl;

import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.service.CreditScoreService;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    @Override
    public int calculateCreditScore(Application application) {
        return 750;
    }

}
