package application.organization.handlers;

import application.organization.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Map<Class<? extends Exception>, HttpStatus> errors = new HashMap<>();

    static {
        errors.put(BadRequestException.class, HttpStatus.BAD_REQUEST);
        errors.put(NotFoundException.class, HttpStatus.NOT_FOUND);
        errors.put(CommonException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        errors.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        errors.put(InvalidActionException.class, HttpStatus.BAD_REQUEST);
        errors.put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
       HttpStatus status = errors.get(exception.getClass());

        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                status.value()
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}