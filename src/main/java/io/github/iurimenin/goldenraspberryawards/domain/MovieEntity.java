package io.github.iurimenin.goldenraspberryawards.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MovieEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "release_year", nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String studios;

    @Column(nullable = false)
    private String producers;

    @Column(nullable = false)
    private boolean winner;

    public MovieEntity() {}

    public MovieEntity(Integer year,
                       String title,
                       String studios,
                       String producers,
                       boolean winner) {

        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getStudios() {
        return studios;
    }

    public String getProducers() {
        return producers;
    }

    public boolean isWinner() {
        return winner;
    }
}
