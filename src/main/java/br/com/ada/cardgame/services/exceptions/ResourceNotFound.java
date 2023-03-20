package br.com.ada.cardgame.services.exceptions;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String value) {
        super(value);
    }
}