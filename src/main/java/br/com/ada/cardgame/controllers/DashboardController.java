package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.RankingResponse;
import br.com.ada.cardgame.services.RoundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DashboardController implements DashboardControllerDoc {

    private final RoundService roundService;

    public RankingResponse getRanking() {
        return new RankingResponse(this.roundService.findRanking().stream()
                .map(ranking -> new RankingResponse.RankingRow(ranking.username(),
                        ranking.percentage())).toList());
    }
}
