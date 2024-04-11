package com.gathigai.cards.utils.web.errors;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    public BadRequestException(){
        super("Sorry, bad request.");
    }

    public BadRequestException(@NotNull String message){
        super(message);
    }

    public BadRequestException(@NotNull String message, Throwable cause){
        super(message, cause);
    }
}
