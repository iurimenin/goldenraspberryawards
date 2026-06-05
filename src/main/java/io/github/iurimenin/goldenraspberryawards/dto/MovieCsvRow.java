package io.github.iurimenin.goldenraspberryawards.dto;

import io.github.iurimenin.goldenraspberryawards.domain.MovieEntity;

public record MovieCsvRow(
        Integer year,
        String title,
        String studios,
        String producers,
        boolean winner
){
    public MovieCsvRow(String[] dados) {
        this(Integer.parseInt(dados[0]),
                dados[1],
                dados[2],
                dados[3],
                dados.length > 4 && "yes".equalsIgnoreCase(dados[4].trim()));
    }

    public MovieEntity toEntity() {
        return new MovieEntity(year, title, studios, producers, winner);
    }
}
