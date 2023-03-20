package br.com.ada.cardgame.services.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String value) {
        super(value);
    }
}
