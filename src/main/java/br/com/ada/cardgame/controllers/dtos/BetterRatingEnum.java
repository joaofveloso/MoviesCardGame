package br.com.ada.cardgame.controllers.dtos;

import lombok.Getter;

@Getter
public enum BetterRatingEnum {
    FIRST_MOVIE(1),
    SECOND_MOVIE(-1),
    DRAW(0);

    private final int compareTo;

    BetterRatingEnum(int compareTo) {
        this.compareTo = compareTo;
    }
}
