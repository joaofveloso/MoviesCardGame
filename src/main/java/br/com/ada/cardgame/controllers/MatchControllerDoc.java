package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.ListMatchResponse;
import br.com.ada.cardgame.controllers.dtos.MatchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/match")
@SecurityScheme(name = HttpHeaders.AUTHORIZATION, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public interface MatchControllerDoc {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "Create a new game match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game match created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MatchResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Forbidden", content = @Content)})
    MatchResponse createMatch(
            @RequestHeader(HttpHeaders.AUTHORIZATION) @Parameter(hidden = true) String authorization);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "Get all matches", responses = {
            @ApiResponse(responseCode = "200", description = "Matches found"),
            @ApiResponse(responseCode = "401", description = "Forbidden")})
    ListMatchResponse getMatches(
            @RequestHeader(HttpHeaders.AUTHORIZATION) @Parameter(hidden = true) String authorization);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "Get a match by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Match found"),
            @ApiResponse(responseCode = "401", description = "Forbidden"),
            @ApiResponse(responseCode = "403", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found")})
    MatchResponse getMatch(
            @RequestHeader(HttpHeaders.AUTHORIZATION) @Parameter(hidden = true) String authorization,
            @PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "Stop a match by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Match stopped"),
            @ApiResponse(responseCode = "401", description = "Forbidden"),
            @ApiResponse(responseCode = "403", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found")})
    void stopMatch(
            @RequestHeader(HttpHeaders.AUTHORIZATION) @Parameter(hidden = true) String authorization,
            @PathVariable("id") Long id);
}

