package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.BetterRatingEnum;
import br.com.ada.cardgame.controllers.dtos.RoundResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/round")
@SecurityScheme(name = HttpHeaders.AUTHORIZATION, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public interface RoundControllerDoc {

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "Guess a better review movie", responses = {
            @ApiResponse(responseCode = "200", description = "Round updated"),
            @ApiResponse(responseCode = "400", description = "Invalid guess"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Match or round not found")
    })
    RoundResponse guessBetterReviewMovie(
            @RequestHeader(HttpHeaders.AUTHORIZATION) @Parameter(hidden = true) String authorization,
            @PathVariable("id") Long id, @RequestParam("guess") BetterRatingEnum guess);
}
