package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

public class ApiRequestException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * @param message the detail message.
     */
    public ApiRequestException(String message) {
        super(message);
    }
    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * @param message the detail message
     * @param cause   the cause
     * @since 1.4
     */
    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
