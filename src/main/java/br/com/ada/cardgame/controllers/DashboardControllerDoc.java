package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.RankingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RequestMapping("/dashboard")
@SecurityScheme(name = HttpHeaders.AUTHORIZATION, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public interface DashboardControllerDoc {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "Get ranking", responses = {
            @ApiResponse(responseCode = "200", description = "Users Raking")})
    RankingResponse getRanking();
}
