package application.organization.exceptions;

public class InvalidActionException extends RuntimeException{
    public InvalidActionException(String message) {
        super(message);
    }
}
