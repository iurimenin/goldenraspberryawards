package io.github.iurimenin.goldenraspberryawards.service;

import io.github.iurimenin.goldenraspberryawards.domain.MovieEntity;
import io.github.iurimenin.goldenraspberryawards.dto.AwardIntervalsResponseDTO;
import io.github.iurimenin.goldenraspberryawards.dto.ProducerIntervalDTO;
import io.github.iurimenin.goldenraspberryawards.dto.WinEntry;
import io.github.iurimenin.goldenraspberryawards.repository.MovieRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

@Service
public class AwardService {

    private final MovieRepository movieRepository;

    public AwardService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public AwardIntervalsResponseDTO getAwardIntervals() {

        List<MovieEntity> winners = movieRepository.findAllByWinnerTrue();

        List<WinEntry> entries = winners.stream()
                .flatMap(movie -> parseProducers(movie.getProducers()).stream()
                        .map(producer -> new WinEntry(producer, movie.getYear())))
                .toList();

        List<ProducerIntervalDTO> intervals = calculateIntervals(entries);

        if (intervals.isEmpty()) {
            return new AwardIntervalsResponseDTO(List.of(), List.of());
        }

        IntSummaryStatistics stats = intervals.stream()
                .mapToInt(ProducerIntervalDTO::interval)
                .summaryStatistics();

        return new AwardIntervalsResponseDTO(
                intervals.stream().filter(p -> p.interval() == stats.getMin()).toList(),
                intervals.stream().filter(p -> p.interval() == stats.getMax()).toList()
        );
    }

    private List<ProducerIntervalDTO> calculateIntervals(List<WinEntry> entries) {
        List<WinEntry> sorted = entries.stream()
                .sorted(Comparator.comparing(WinEntry::producer).thenComparingInt(WinEntry::year))
                .toList();

        return IntStream.range(1, sorted.size())
                .filter(i -> sorted.get(i).producer().equals(sorted.get(i - 1).producer()))
                .mapToObj(i -> new ProducerIntervalDTO(
                        sorted.get(i).producer(),
                        sorted.get(i).year() - sorted.get(i - 1).year(),
                        sorted.get(i - 1).year(),
                        sorted.get(i).year()))
                .toList();
    }

    private List<String> parseProducers(@Nonnull String producers) {

        if (producers.isBlank()) {
            return List.of();
        }

        String[] producerNames = producers.split(",|\\s+and\\s+");

        return Arrays.stream(producerNames)
                .map(String::trim)
                .filter(name -> !name.isBlank())
                .toList();
    }
}
