package org.example.loanapplicationservice.config;

import org.example.loanapplicationservice.decorator.LoggingApplicationService;
import org.example.loanapplicationservice.service.ApplicationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ApplicationConfig {

    @Primary
    @Bean
    public ApplicationService loggingApplicationService(
        @Qualifier("applicationServiceImpl") ApplicationService applicationService
    ) {
        return new LoggingApplicationService(applicationService);
    }

}