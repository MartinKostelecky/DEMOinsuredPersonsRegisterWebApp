package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

public class EmailAlreadyTakenException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * @param message the detail message.
     */
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * @param message the detail message
     * @param cause   the cause
     * @since 1.4
     */
    public EmailAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
