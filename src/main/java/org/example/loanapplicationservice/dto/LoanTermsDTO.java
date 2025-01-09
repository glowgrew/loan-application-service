package org.example.loanapplicationservice.dto;

import java.math.BigDecimal;

public record LoanTermsDTO(BigDecimal interestRate, int loanDurationMonths) { }
