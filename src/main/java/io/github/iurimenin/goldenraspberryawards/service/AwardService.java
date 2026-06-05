package io.github.iurimenin.goldenraspberryawards.service;

import io.github.iurimenin.goldenraspberryawards.domain.MovieEntity;
import io.github.iurimenin.goldenraspberryawards.dto.AwardIntervalsResponseDTO;
import io.github.iurimenin.goldenraspberryawards.dto.ProducerIntervalDTO;
import io.github.iurimenin.goldenraspberryawards.repository.MovieRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AwardService {

    private final MovieRepository movieRepository;

    public AwardService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public AwardIntervalsResponseDTO getAwardIntervals() {

        List<MovieEntity> winners = movieRepository.findAllByWinnerTrue();

        Map<String, List<Integer>> winsByProducer = new HashMap<>();

        winners.forEach(movieEntity -> {

            List<String> producers = this.handleProducers(movieEntity.getProducers());
            producers.forEach(producer -> {
                winsByProducer.computeIfAbsent(producer, key -> new ArrayList<>()).add(movieEntity.getYear());
            });
        });

        List<ProducerIntervalDTO> intervals = this.handleIntervals(winsByProducer);

        if (intervals.isEmpty()) {
            return new AwardIntervalsResponseDTO(
                    Collections.emptyList(),
                    Collections.emptyList()
            );
        }

        int minInterval = intervals.stream()
                .mapToInt(ProducerIntervalDTO::interval)
                .min()
                .orElseThrow();

        int maxInterval = intervals.stream()
                .mapToInt(ProducerIntervalDTO::interval)
                .max()
                .orElseThrow();

        List<ProducerIntervalDTO> min = intervals.stream()
                .filter(interval ->
                        interval.interval() == minInterval)
                .toList();

        List<ProducerIntervalDTO> max = intervals.stream()
                .filter(interval ->
                        interval.interval() == maxInterval)
                .toList();

        return new AwardIntervalsResponseDTO(min, max);
    }

    private List<ProducerIntervalDTO> handleIntervals(Map<String, List<Integer>> winsByProducer) {

        List<ProducerIntervalDTO> intervals = new ArrayList<>();

        winsByProducer.forEach((producer, years) -> {

            if (years.size() >= 2) {

                List<Integer> sortedYears = years.stream().sorted().toList();
                for (int i = 1; i < sortedYears.size(); i++) {
                    Integer previousWin = sortedYears.get(i - 1);
                    Integer followingWin = sortedYears.get(i);

                    intervals.add(
                            new ProducerIntervalDTO(
                                    producer,
                                    followingWin - previousWin,
                                    previousWin,
                                    followingWin
                            )
                    );
                }
            }
        });

        return intervals;
    }

    private List<String> handleProducers(@Nonnull String producers) {
        
        if (producers.isEmpty()) {
            return new ArrayList<>();
        }

        String normalizedProducers = producers.replaceAll("\\s+and\\s+", ",");
        String[] producerNames = normalizedProducers.split(",");

        return Arrays.stream(producerNames)
                .map(String::trim)
                .filter(name -> !name.isBlank())
                .toList();
    }
}
