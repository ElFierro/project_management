package co.edu.talentotech.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.edu.talentotech.response.ResponseDetails;
import co.edu.talentotech.response.ResponseMessages;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDetails> handleDateTimeParseException(DateTimeParseException ex) {
        String timestamp = LocalDateTime.now().toString();
        ResponseDetails responseDetails = new ResponseDetails(ResponseMessages.CODE_404, ResponseMessages.ERROR_FORMAT_DATE);
        return ResponseEntity.badRequest().body(responseDetails);
    }
}