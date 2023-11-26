package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

import cz.martinkostelecky.insuredpersonsregisterwebapp.entity.Insurance;

public class InsuranceNotFoundException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * @param message the detail message.
     */
    public InsuranceNotFoundException(String message) {
        super(message);
    }
    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * @param message the detail message
     * @param cause   the cause
     * @since 1.4
     */
    public InsuranceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

