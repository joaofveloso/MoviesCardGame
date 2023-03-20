package br.com.ada.cardgame.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

@Schema(description = "Represents a round")
public record RoundResponse(
        @Schema(description = "Whether the answer was correct or not", example = "true", requiredMode = RequiredMode.REQUIRED) boolean rightAnswer,
        @Schema(description = "The number of wrong answers for the round", example = "1", requiredMode = RequiredMode.REQUIRED) int wrongCount,
        @Schema(description = "The list of movies for the round", requiredMode = RequiredMode.REQUIRED) List<MovieResponse> movies) {

}
