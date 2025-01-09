package org.example.loanapplicationservice.dto;

import java.math.BigDecimal;

public record ApplicationDTO(Long id, String name, String email, BigDecimal requestedAmount, String status) { }
