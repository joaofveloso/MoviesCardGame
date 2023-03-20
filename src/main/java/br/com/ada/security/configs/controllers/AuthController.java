package br.com.ada.security.configs.controllers;

import br.com.ada.security.configs.controllers.dtos.AuthRequest;
import br.com.ada.security.services.TokenService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Map<String, String> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        return createResponse(request.username());
    }

    private Map<String, String> createResponse(String value) {
        return Map.of("token", tokenService.createJwtToken(value));
    }
}
