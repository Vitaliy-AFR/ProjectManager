package com.example.ProjectManager.Exceptions;

import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class PMExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException (NotFoundException ex) {
        var reason = ex.getMessage();
        log.error(reason);
        return new ResponseEntity<>(reason, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException (BadRequestException ex){
        var reason = ex.getMessage();
        log.error(reason);
        return new ResponseEntity<>(reason, HttpStatus.BAD_REQUEST);
    }
}
