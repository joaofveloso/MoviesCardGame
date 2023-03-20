package br.com.ada.cardgame.services.impl;

import br.com.ada.cardgame.repositories.RoundRepository;
import br.com.ada.cardgame.repositories.entities.MatchEntity;
import br.com.ada.cardgame.repositories.entities.MovieEntity;
import br.com.ada.cardgame.repositories.entities.RoundEntity;
import br.com.ada.cardgame.services.RoundService;
import br.com.ada.cardgame.services.dtos.BestRattingDto;
import br.com.ada.cardgame.services.dtos.RankingRoundDto;
import br.com.ada.cardgame.services.dtos.RoundDto;
import br.com.ada.cardgame.services.exceptions.BadRequestException;
import br.com.ada.cardgame.services.exceptions.NotAuthorized;
import br.com.ada.cardgame.services.exceptions.ResourceNotFound;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService {

    private final MovieServiceImpl movieService;
    private final RoundRepository repository;
    private final PlatformTransactionManager transactionManager;

    private final int rowsCount = 85;
    @Value("${core.failure.max.count:3}")
    private int failuresMaxCount;

    protected RoundEntity createRound(MatchEntity matchEntity) {
        List<MovieEntity> movies = new ArrayList<>();
        while (movies.size() < 2) {
            MovieEntity randomMovie = this.movieService.findById(
                    new Random().nextLong(1, rowsCount));
            if (movies.stream().anyMatch(movie -> movie.getId().equals(randomMovie.getId()))) {
                continue;
            }
            movies.add(randomMovie);
        }
        return repository.save(new RoundEntity(movies.get(0), movies.get(1), matchEntity));
    }

    protected static RoundDto toDto(RoundEntity roundEntity) {
        if (Objects.isNull(roundEntity)) {
            return null;
        }
        return new RoundDto(roundEntity.getId(),
                MovieServiceImpl.toDto(roundEntity.getFirstMovie()),
                MovieServiceImpl.toDto(roundEntity.getSecondMovie()));
    }

    protected RoundEntity findLastRoundByMatchId(Long id) {
        return repository.findTopByMatchEntity_IdOrderByIdDesc(id)
                .orElseThrow(() -> new ResourceNotFound("Round not found"));
    }

    @Override
    public BestRattingDto guessBetterReviewMovie(Long id, int guess) {
        RoundEntity roundEntity = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Round not found"));
        if (Objects.nonNull(roundEntity.getRightResponse())) {
            throw new BadRequestException("This quiz have been already answered");
        }

        MovieEntity movie1 = roundEntity.getFirstMovie();
        MovieEntity movie2 = roundEntity.getSecondMovie();

        boolean rightAnswer = guess == movie1.getRating().compareTo(movie2.getRating());
        roundEntity.setRightResponse(rightAnswer);
        saveAndUpdate(roundEntity);

        Integer wrongAnswersCount = this.repository.countAllByMatchEntityAndRightResponseIsFalse(
                roundEntity.getMatchEntity());
        RoundEntity newRoundEntity =
                wrongAnswersCount < failuresMaxCount ? createRound(roundEntity.getMatchEntity())
                        : null;
        return new BestRattingDto(rightAnswer, wrongAnswersCount, toDto(newRoundEntity));
    }

    public void saveAndUpdate(RoundEntity roundEntity) {

        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                repository.saveAndFlush(roundEntity);
            }
        });
    }

    @Override
    public void validateOwnership(String username, Long id) {
        RoundEntity roundEntity = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Round not found"));
        if (!username.equals(roundEntity.getUserValue())) {
            throw new NotAuthorized("User not authorized to access this resource");
        }
    }

    @Override
    public List<RankingRoundDto> findRanking() {

        List<RoundEntity> all = repository.findAll();
        Map<String, List<RoundEntity>> collect = all.stream()
                .collect(Collectors.groupingBy(RoundEntity::getUserValue));
        return collect.entrySet().stream().map(set -> {
                    int size = set.getValue().size();
                    List<RoundEntity> rightAnswers = set.getValue().stream()
                            .filter(p -> p.getRightResponse() != null && p.getRightResponse()).toList();
                    return new RankingRoundDto(set.getKey(),
                            new BigDecimal((double) rightAnswers.size() / size));
                }).sorted(Comparator.comparing(RankingRoundDto::percentage, Collections.reverseOrder()))
                .toList();
    }

    public List<RoundEntity> findAllRoundByMatchId(MatchEntity match) {
        return this.repository.findAllByMatchEntity(match);
    }
}
