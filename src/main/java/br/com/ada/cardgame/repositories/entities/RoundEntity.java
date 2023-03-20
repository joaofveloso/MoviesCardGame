package br.com.ada.cardgame.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "round_data")
@NoArgsConstructor
public class RoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private MovieEntity firstMovie;
    @OneToOne
    private MovieEntity secondMovie;
    private String userValue;
    @ManyToOne(optional = false)
    private MatchEntity matchEntity;
    @Column(unique = true)
    private int hashValue;
    private Boolean rightResponse;

    public RoundEntity(MovieEntity firstMovie, MovieEntity secondMovie, MatchEntity matchEntity) {
        this.firstMovie = firstMovie;
        this.secondMovie = secondMovie;
        this.userValue = matchEntity.getUserValue();
        this.matchEntity = matchEntity;
    }

    @PrePersist
    protected void prePersist() {
        List<MovieEntity> movieList = new ArrayList<>(List.of(firstMovie, secondMovie));
        movieList.sort(Comparator.comparing(MovieEntity::getId));
        this.hashValue = Objects.hash(movieList.get(0).getId(), userValue, movieList.get(1).getId());
    }
}
