package br.com.ada.cardgame.services.impl;

import br.com.ada.cardgame.repositories.MatchRepository;
import br.com.ada.cardgame.repositories.entities.MatchEntity;
import br.com.ada.cardgame.repositories.entities.RoundEntity;
import br.com.ada.cardgame.services.MatchService;
import br.com.ada.cardgame.services.dtos.MatchAnalyticsDto;
import br.com.ada.cardgame.services.dtos.MatchCurrentRoundDto;
import br.com.ada.cardgame.services.exceptions.NotAuthorized;
import br.com.ada.cardgame.services.exceptions.ResourceNotFound;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final RoundServiceImpl roundService;
    private final MatchRepository matchRepository;

    @Override
    public MatchCurrentRoundDto createMatch(String username) {
        MatchEntity matchEntity = this.matchRepository.save(new MatchEntity(username));
        RoundEntity round = this.roundService.createRound(matchEntity);
        return toDto(matchEntity, round);
    }

    @Override
    public List<MatchAnalyticsDto> findByUsername(String username) {
        List<MatchEntity> byUsername = this.matchRepository.findAllByUserValueOrderById(username);
        return byUsername.stream().map(this::prepareMatchAnalyticsDto).toList();
    }

    private MatchAnalyticsDto prepareMatchAnalyticsDto(MatchEntity match) {
        List<RoundEntity> rounds = this.roundService.findAllRoundByMatchId(match);
        Integer roundSize = rounds.size();
        Integer rightResponse = Math.toIntExact(rounds.stream()
                .filter(round -> Objects.nonNull(round.getRightResponse())
                        && round.getRightResponse()).count());
        return new MatchAnalyticsDto(match.getId(), roundSize, rightResponse, match.isFinished());
    }

    @Override
    public MatchCurrentRoundDto findById(Long id) {
        Optional<MatchEntity> matches = this.matchRepository.findById(id);
        return matches.map(match -> {
            RoundEntity lastRound = this.roundService.findLastRoundByMatchId(match.getId());
            return MatchServiceImpl.toDto(match, lastRound);
        }).orElseThrow(() -> new ResourceNotFound("Match not found"));
    }

    @Override
    public void stopMatch(Long id) {
        MatchEntity matchEntity = this.matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Match not found"));
        matchEntity.setFinished(true);
        this.matchRepository.save(matchEntity);

    }

    protected static MatchCurrentRoundDto toDto(MatchEntity movieEntity, RoundEntity round) {
        return new MatchCurrentRoundDto(movieEntity.getId(), RoundServiceImpl.toDto(round),
                movieEntity.getUserValue());
    }

    public void validateOwnership(String username, Long id) {
        MatchEntity match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Match not found"));
        if (!Objects.equals(username, match.getUserValue())) {
            throw new NotAuthorized("User not authorized to access this resource");
        }
    }
}
