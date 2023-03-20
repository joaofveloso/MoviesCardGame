package br.com.ada.security.services;

public interface TokenService {

    String createJwtToken(String username);

    String extractUsername(String token);
}
