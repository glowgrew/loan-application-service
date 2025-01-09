package org.example.loanapplicationservice.dto;

import java.math.BigDecimal;

public record ApplicationRequestDTO(String name, String email, BigDecimal requestedAmount) { }
