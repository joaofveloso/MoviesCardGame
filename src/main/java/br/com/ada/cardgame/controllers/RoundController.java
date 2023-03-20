package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.BetterRatingEnum;
import br.com.ada.cardgame.controllers.dtos.RoundResponse;
import br.com.ada.cardgame.services.RoundService;
import br.com.ada.cardgame.services.dtos.BestRattingDto;
import br.com.ada.cardgame.services.exceptions.LossException;
import br.com.ada.security.services.TokenService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoundController implements RoundControllerDoc {

    private final TokenService tokenService;
    private final RoundService roundService;

    public RoundResponse guessBetterReviewMovie(String authorization, Long id,
            BetterRatingEnum guess) {

        String username = tokenService.extractUsername(authorization);
        this.roundService.validateOwnership(username, id);

        BestRattingDto round = this.roundService.guessBetterReviewMovie(id, guess.getCompareTo());
        if (Objects.isNull(round.round())) {
            throw new LossException("You have failed");
        }
        return new RoundResponse(round.rightAnswer(), round.wrongCount(),
                ControllerUtilities.toResponse(round.round().movie1(),
                        round.round().movie2()));
    }
}
