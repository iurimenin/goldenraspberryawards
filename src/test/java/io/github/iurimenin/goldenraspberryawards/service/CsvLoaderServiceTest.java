package io.github.iurimenin.goldenraspberryawards.service;

import io.github.iurimenin.goldenraspberryawards.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CsvLoaderServiceTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void shouldLoadMoviesFromCsv() {
        assertThat(movieRepository.count()).isGreaterThan(0);
    }

}