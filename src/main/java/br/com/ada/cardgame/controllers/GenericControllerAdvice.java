package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.MessageResponse;
import br.com.ada.cardgame.services.exceptions.BadRequestException;
import br.com.ada.cardgame.services.exceptions.LossException;
import br.com.ada.cardgame.services.exceptions.NotAuthorized;
import br.com.ada.cardgame.services.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GenericControllerAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(LossException.class)
    public MessageResponse handleLossException(RuntimeException lossException) {
        return new MessageResponse(lossException.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorized.class)
    public MessageResponse handleNotAuthorized(RuntimeException notAuthorized) {
        return new MessageResponse(notAuthorized.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public MessageResponse handleRoundFinished(RuntimeException notAuthorized) {
        return new MessageResponse(notAuthorized.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFound.class)
    public MessageResponse handleResourceNotFound(RuntimeException notAuthorized) {
        return new MessageResponse(notAuthorized.getMessage());
    }
}
