package br.com.ada.cardgame.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(description = "Represents a movie")
public record MovieResponse(
        @Schema(description = "The ID of the movie", example = "24", requiredMode = RequiredMode.REQUIRED) Long id,
        @Schema(description = "The title of the movie", example = "Toy Story 3", requiredMode = RequiredMode.REQUIRED) String title,
        @Schema(description = "The URL of the movie's poster image", example = "https://...", requiredMode = RequiredMode.REQUIRED) String poster) {

}