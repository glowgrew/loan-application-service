package org.example.loanapplicationservice.validation;

import org.example.loanapplicationservice.entity.Application;
import java.util.regex.Pattern;

public class EmailValidationHandler extends ValidationHandler {

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public ValidationResult validate(Application application) {
        if (!EMAIL_PATTERN.matcher(application.getEmail()).matches()) {
            return new ValidationResult(false, "Invalid email format");
        }
        return nextHandler != null ? nextHandler.validate(application) 
                                 : new ValidationResult(true, "Validation successful");
    }

}

