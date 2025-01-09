package org.example.loanapplicationservice.builder;

import org.example.loanapplicationservice.entity.Application;
import java.math.BigDecimal;

public class ApplicationBuilder {

    private Long id;
    private String name;
    private String email;
    private BigDecimal requestedAmount;
    private String status;
    
    public ApplicationBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    
    public ApplicationBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public ApplicationBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    
    public ApplicationBuilder withRequestedAmount(BigDecimal amount) {
        this.requestedAmount = amount;
        return this;
    }
    
    public ApplicationBuilder withStatus(String status) {
        this.status = status;
        return this;
    }
    
    public Application build() {
        return new Application(id, name, email, requestedAmount, status);
    }

}

