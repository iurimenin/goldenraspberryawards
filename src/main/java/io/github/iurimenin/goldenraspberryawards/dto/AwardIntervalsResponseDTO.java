package io.github.iurimenin.goldenraspberryawards.dto;

import java.util.List;

public record AwardIntervalsResponseDTO (
        List<ProducerIntervalDTO> min,
        List<ProducerIntervalDTO> max
) {
}