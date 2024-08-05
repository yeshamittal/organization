package application.organization.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidActionException extends RuntimeException{
    public InvalidActionException(String message) {
        super(message);
    }
}
