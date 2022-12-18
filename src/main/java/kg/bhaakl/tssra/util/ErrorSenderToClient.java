package kg.bhaakl.tssra.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorSenderToClient {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage() == null? error.getCode(): error.getDefaultMessage())
                        .append("; ");
            }

            throw new UserException(errorMsg.toString());
        }
    }
}
