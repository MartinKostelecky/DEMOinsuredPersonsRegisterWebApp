package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

public class BadRequestException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * @param message the detail message.
     */
    public BadRequestException(String message) {
        super(message);
    }
    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * @param message the detail message
     * @param cause   the cause
     * @since 1.4
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
