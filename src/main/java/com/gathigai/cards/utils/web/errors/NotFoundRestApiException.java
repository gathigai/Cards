package com.gathigai.cards.utils.web.errors;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundRestApiException extends RuntimeException{

    public NotFoundRestApiException(@NotNull String message){
        super(message);
    }

    public NotFoundRestApiException(@NotNull String message, Throwable cause){
        super(message, cause);
    }
}
