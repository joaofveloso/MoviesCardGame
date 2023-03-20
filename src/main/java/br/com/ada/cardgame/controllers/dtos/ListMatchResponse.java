package br.com.ada.cardgame.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

@Schema(name = "Represent list of all all matches with their analytics")
public record ListMatchResponse(
        @Schema(description = "The list of analytics for each match") List<Analytics> analytics) {

    @Schema(name = "Match analytics")
    public record Analytics(@Schema(description = "The ID of the match") Long id,
                            @Schema(description = "The status of the match") String status,
                            @Schema(description = "The number of rounds in the match") Integer roundCount,
                            @Schema(description = "The percentage of correct answers in the match") BigDecimal rightAnswers) {

    }
}