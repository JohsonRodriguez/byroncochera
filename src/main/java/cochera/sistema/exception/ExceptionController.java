package cochera.sistema.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundErrorHandling(UserNotFoundException exception) {
        var errorDetails = new ErrorDetails(
                new Date(),
                "System Garage Byron exception!",
                exception.getMessage()
        );
        return ResponseEntity.badRequest().body(errorDetails);
    }
}

