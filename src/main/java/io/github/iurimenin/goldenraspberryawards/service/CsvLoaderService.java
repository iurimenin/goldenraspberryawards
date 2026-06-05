package io.github.iurimenin.goldenraspberryawards.service;

import io.github.iurimenin.goldenraspberryawards.domain.MovieEntity;
import io.github.iurimenin.goldenraspberryawards.dto.MovieCsvRow;
import io.github.iurimenin.goldenraspberryawards.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvLoaderService implements ApplicationRunner {

    @Value("${app.csv-path}")
    private String csvPath;

    private final MovieRepository movieRepository;

    public CsvLoaderService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvPath);
        if (inputStream == null) {
            throw new IllegalStateException("Arquivo CSV não encontrado: " + csvPath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            List<MovieEntity> movies = reader.lines()
                    .skip(1)
                    .map(this::parseLine)
                    .map(MovieCsvRow::toEntity)
                    .toList();

            movieRepository.saveAll(movies);
        }
    }

    private MovieCsvRow parseLine(String line) {

        String[] columns = line.split(";");

        if (columns.length < 4 || columns.length > 5) {
            throw new IllegalArgumentException("Linha CSV inválida: " + line);
        }

        return new MovieCsvRow(columns);
    }
}
