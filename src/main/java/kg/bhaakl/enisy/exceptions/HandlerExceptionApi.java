package kg.bhaakl.enisy.exceptions;

import kg.bhaakl.enisy.util.UserErrorResponse;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

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
                .body(new ErrorMessage(Objects.requireNonNull(e.getMessage())));
    }

    @ExceptionHandler({UsernameNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<UserErrorResponse> userNotFoundException(RuntimeException e) {
        UserErrorResponse userError = new UserErrorResponse(false, e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(userError);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserErrorResponse> userErrorException(UserException e) {
        UserErrorResponse userError = new UserErrorResponse(false, e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(userError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<UserErrorResponse> userBadCredentialErrorException(BadCredentialsException e) {
        UserErrorResponse userError = new UserErrorResponse(false, e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(userError);
    }

    @ExceptionHandler({MyUnAuthorizedException.class})
    public ResponseEntity<UserErrorResponse> userUnAuthorizedException(MyUnAuthorizedException e) {
        UserErrorResponse userError = new UserErrorResponse(false, e.getMessage(), System.currentTimeMillis());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(userError);
    }

    /*
    @ExceptionHandler({AuthenticationException.class, MissingCsrfTokenException.class, InvalidCsrfTokenException.class, SessionAuthenticationException.class})
    public ErrorInfo handleAuthenticationException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        this.tokenRepository.clearToken(response);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return new ErrorInfo(UrlUtils.buildFullRequestUrl(request), "error.authorization");
    }

    @Getter
    public class ErrorInfo {
        private final String url;
        private final String info;

        ErrorInfo(String url, String info) {
            this.url = url;
            this.info = info;
        }
    }*/

}
