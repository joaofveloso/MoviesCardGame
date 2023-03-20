package br.com.ada.cardgame.controllers.dtos;

import java.math.BigDecimal;
import java.util.List;

public record RankingResponse(List<RankingRow> rows) {

    public record RankingRow(String username, BigDecimal rightAnswers) {

    }
}
