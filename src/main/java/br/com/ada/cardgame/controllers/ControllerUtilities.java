package br.com.ada.cardgame.controllers;

import br.com.ada.cardgame.controllers.dtos.MovieResponse;
import br.com.ada.cardgame.services.dtos.MovieDto;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerUtilities {

    public static MovieResponse toResponse(MovieDto movie) {

        return new MovieResponse(movie.id(), movie.title(), movie.poster());
    }

    public static List<MovieResponse> toResponse(MovieDto... movies) {

        return Stream.of(movies).map(ControllerUtilities::toResponse).toList();
    }
}
