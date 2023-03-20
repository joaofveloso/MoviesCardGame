package br.com.ada.cardgame.services.impl;

import br.com.ada.cardgame.repositories.MovieRepository;
import br.com.ada.cardgame.repositories.entities.MovieEntity;
import br.com.ada.cardgame.services.dtos.MovieDto;
import br.com.ada.cardgame.services.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl {

    private final MovieRepository movieRepository;

    protected static MovieDto toDto(MovieEntity movieEntity) {
        return new MovieDto(movieEntity.getId(), movieEntity.getTitle(), movieEntity.getPoster());
    }

    protected MovieEntity findById(long nextLong) {
        return this.movieRepository.findById(nextLong)
                .orElseThrow(() -> new ResourceNotFound("Movie not found"));
    }
}
