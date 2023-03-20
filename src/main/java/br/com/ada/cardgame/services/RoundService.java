package br.com.ada.cardgame.services;

import br.com.ada.cardgame.services.dtos.BestRattingDto;
import br.com.ada.cardgame.services.dtos.RankingRoundDto;
import java.util.List;

public interface RoundService {

    BestRattingDto guessBetterReviewMovie(Long id, int guess);

    void validateOwnership(String username, Long id);

    List<RankingRoundDto> findRanking();
}
