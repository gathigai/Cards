package com.gathigai.cards.utils.web.errors;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenRestApiException extends RuntimeException{

    public ForbiddenRestApiException(@NotNull String message){
        super(message);
    }

    public ForbiddenRestApiException(@NotNull String message, Throwable cause){
        super(message, cause);
    }
}
