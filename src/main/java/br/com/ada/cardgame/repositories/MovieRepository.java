package br.com.ada.cardgame.repositories;

import br.com.ada.cardgame.repositories.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query(value = """
            SELECT *
            FROM movie
            ORDER BY RANDOM() LIMIT 1
            """, nativeQuery = true)
    MovieEntity findRandom();
}
