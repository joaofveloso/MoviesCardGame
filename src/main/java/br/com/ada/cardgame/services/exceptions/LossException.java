package br.com.ada.cardgame.services.exceptions;

public class LossException extends RuntimeException {

    public LossException(String value) {
        super(value);
    }
}
