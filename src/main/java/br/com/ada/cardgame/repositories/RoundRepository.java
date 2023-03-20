package br.com.ada.cardgame.repositories;

import br.com.ada.cardgame.repositories.entities.MatchEntity;
import br.com.ada.cardgame.repositories.entities.RoundEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<RoundEntity, Long> {

    Optional<RoundEntity> findTopByMatchEntity_IdOrderByIdDesc(Long id);

    Integer countAllByMatchEntityAndRightResponseIsFalse(MatchEntity matchEntity);

    List<RoundEntity> findAllByMatchEntity(MatchEntity match);
}
