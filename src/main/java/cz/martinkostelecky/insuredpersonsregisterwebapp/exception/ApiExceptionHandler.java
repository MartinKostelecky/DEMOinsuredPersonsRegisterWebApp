package cz.martinkostelecky.insuredpersonsregisterwebapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException (BadRequestException e) {
        //Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                //throwable e
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = InsuredPersonNotFoundException.class)
    public ResponseEntity<Object> handleInsuredPersonNotFoundException (InsuredPersonNotFoundException e) {
        //Create payload containing exception details
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                //throwable e
                e,
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //Return response entity
        return new ResponseEntity<>(apiException, notFound);
    }



}
