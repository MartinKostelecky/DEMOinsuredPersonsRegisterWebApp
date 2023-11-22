package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

public class InsuredPersonNotFoundException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * @param message the detail message.
     */
    public InsuredPersonNotFoundException(String message) {
        super(message);
    }
    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * @param message the detail message
     * @param cause   the cause
     * @since 1.4
     */
    public InsuredPersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
