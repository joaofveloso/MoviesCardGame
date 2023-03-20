package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.ListMatchResponse;
import br.com.ada.cardgame.controllers.dtos.ListMatchResponse.Analytics;
import br.com.ada.cardgame.controllers.dtos.MatchResponse;
import br.com.ada.cardgame.controllers.dtos.MovieResponse;
import br.com.ada.cardgame.services.MatchService;
import br.com.ada.cardgame.services.dtos.MatchAnalyticsDto;
import br.com.ada.cardgame.services.dtos.MatchCurrentRoundDto;
import br.com.ada.security.services.TokenService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MatchController implements MatchControllerDoc {

    private final TokenService tokenService;
    private final MatchService matchService;

    public MatchResponse createMatch(String authorization) {
        String username = tokenService.extractUsername(authorization);
        MatchCurrentRoundDto matchCurrentRoundDto = matchService.createMatch(username);
        return new MatchResponse(matchCurrentRoundDto.roundDto().id(),
                matchCurrentRoundDto.roundDto().id(), getMovies(matchCurrentRoundDto));
    }

    public ListMatchResponse getMatches(String authorization) {
        String username = tokenService.extractUsername(authorization);
        List<MatchAnalyticsDto> matches = matchService.findByUsername(username);
        return new ListMatchResponse(
                matches.stream().filter(p -> Objects.nonNull(p.rightAnswer())).map(match -> {
                    String status = match.finished() ? "Closed" : "Open";
                    BigDecimal percentage = BigDecimal.valueOf(
                            (double) match.rightAnswer() / match.rounds());
                    return new Analytics(match.id(), status, match.rounds(), percentage);
                }).toList());
    }

    public MatchResponse getMatch(String authorization, Long id) {
        String username = tokenService.extractUsername(authorization);
        this.matchService.validateOwnership(username, id);
        MatchCurrentRoundDto matchCurrentRoundDto = matchService.findById(id);
        return new MatchResponse(matchCurrentRoundDto.roundDto().id(),
                matchCurrentRoundDto.roundDto().id(), getMovies(matchCurrentRoundDto));
    }

    public void stopMatch(String authorization, Long id) {
        String username = tokenService.extractUsername(authorization);
        this.matchService.validateOwnership(username, id);
        this.matchService.stopMatch(id);
    }

    private static List<MovieResponse> getMovies(MatchCurrentRoundDto matchCurrentRoundDto) {
        return Stream.of(matchCurrentRoundDto.roundDto().movie1(),
                        matchCurrentRoundDto.roundDto().movie2())
                .map(movie -> new MovieResponse(movie.id(), movie.title(), movie.poster()))
                .toList();
    }
}

