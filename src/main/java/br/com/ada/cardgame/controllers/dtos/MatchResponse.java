package br.com.ada.cardgame.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

@Schema(description = "Represents a match with the movies to select")
public record MatchResponse(
        @Schema(description = "The ID of the match", example = "1", requiredMode = RequiredMode.REQUIRED) Long id,
        @Schema(description = "The ID of the current active round", example = "1", requiredMode = RequiredMode.REQUIRED) Long roundId,
        @Schema(description = "The movies to choose from in the match", requiredMode = RequiredMode.REQUIRED) List<MovieResponse> movies) {

}