package br.com.ada.cardgame.repositories;

import br.com.ada.cardgame.repositories.entities.MatchEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    List<MatchEntity> findAllByUserValueOrderById(String username);
}
