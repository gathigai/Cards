package com.gathigai.cards.utils.web;

import com.gathigai.cards.utils.web.errors.BadRequestException;
import com.gathigai.cards.utils.web.errors.NotFoundRestApiException;
import com.gathigai.cards.utils.web.errors.ForbiddenRestApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundRestApiException.class)
    public ResponseEntity<Map<String, List<String>>> handleCardNotFoundException(NotFoundRestApiException exception){
        List<String> errors = Collections.singletonList(exception.getMessage());

        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenRestApiException.class)
    public ResponseEntity<Map<String, List<String>>> handleForbiddenCardException(ForbiddenRestApiException exception){
        List<String> errors = Collections.singletonList(exception.getMessage());

        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, List<String>>> handleBadRequestException(BadRequestException exception){
        List<String> errors = Collections.singletonList(exception.getMessage());

        return new ResponseEntity<>(getErrorsMap(errors), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
