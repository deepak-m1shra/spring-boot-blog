package iam.sde.udemyblog.exception;

import iam.sde.udemyblog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotfound(ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .message(exception.getMessage())
                .description(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> callAllExceptions(Exception ex, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                ex.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status,
//                                                                  WebRequest request) {
//
//        Map<String, String> map = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            map.put(fieldName, message);
//        });
//        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInputValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String key = ((FieldError) error).getField();
            String value = error.getDefaultMessage();
            errorMap.put(key, value);
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
