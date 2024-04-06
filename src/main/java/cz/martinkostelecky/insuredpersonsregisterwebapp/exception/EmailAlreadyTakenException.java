package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

public class EmailAlreadyTakenException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}