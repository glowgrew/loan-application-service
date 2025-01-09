package org.example.loanapplicationservice.config;

import org.example.loanapplicationservice.validation.AmountValidationHandler;
import org.example.loanapplicationservice.validation.EmailValidationHandler;
import org.example.loanapplicationservice.validation.ValidationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {

    @Bean
    public ValidationHandler validationChain() {
        ValidationHandler amountValidator = new AmountValidationHandler();
        ValidationHandler emailValidator = new EmailValidationHandler();
        amountValidator.setNext(emailValidator);
        return amountValidator;
    }

}