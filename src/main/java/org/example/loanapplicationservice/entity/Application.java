package org.example.loanapplicationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private Long id;
    private String name;
    private String email;
    private BigDecimal requestedAmount;
    private String status;

}
