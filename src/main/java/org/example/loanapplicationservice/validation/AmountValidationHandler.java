package org.example.loanapplicationservice.validation;

import org.example.loanapplicationservice.entity.Application;
import java.math.BigDecimal;

public class AmountValidationHandler extends ValidationHandler {

    private static final BigDecimal MIN_AMOUNT = new BigDecimal("1000");
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("50000");

    @Override
    public ValidationResult validate(Application application) {
        if (application.getRequestedAmount().compareTo(MIN_AMOUNT) < 0) {
            return new ValidationResult(false, "Amount is below minimum threshold");
        }
        if (application.getRequestedAmount().compareTo(MAX_AMOUNT) > 0) {
            return new ValidationResult(false, "Amount exceeds maximum threshold");
        }
        return nextHandler != null ? nextHandler.validate(application) 
                                 : new ValidationResult(true, "Validation successful");
    }

}
