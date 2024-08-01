package application.organization.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String errorMessage;
    private int statusCode;
}
