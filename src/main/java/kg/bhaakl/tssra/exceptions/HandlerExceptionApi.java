package kg.bhaakl.tssra.exceptions;

import kg.bhaakl.tssra.util.UserErrorResponse;
import kg.bhaakl.tssra.util.UserException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class HandlerExceptionApi {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(ChangeSetPersister.NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> mismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<UserErrorResponse> userNotFoundException(UsernameNotFoundException e) {
        UserErrorResponse userError = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(userError);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserErrorResponse> userErrorException(UserException e) {
        UserErrorResponse userError = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(userError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<UserErrorResponse> userErrorException(BadCredentialsException e) {
        UserErrorResponse userError = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(userError);
    }

}
