package org.justinbaur.bankteller.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse inputValidation(MethodArgumentNotValidException ex){
        return new ErrorResponse("Failed input validation or something like that", ex.getBindingResult()
        .getAllErrors()
        .stream()
        .map(error -> (FieldError) error).collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProfileNotFound.class)
    public ErrorResponse profileNotFound(ProfileNotFound ex){
        return new ErrorResponse("Profile Not Found", null);
    }
}
