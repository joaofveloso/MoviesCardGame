package br.com.ada.cardgame.services.exceptions;

public class NotAuthorized extends RuntimeException {

    public NotAuthorized(String value) {
        super(value);
    }
}
