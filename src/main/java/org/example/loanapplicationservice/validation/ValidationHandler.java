package org.example.loanapplicationservice.validation;

import org.example.loanapplicationservice.entity.Application;

public abstract class ValidationHandler {

    protected ValidationHandler nextHandler;

    public ValidationHandler setNext(ValidationHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    public abstract ValidationResult validate(Application application);

}
