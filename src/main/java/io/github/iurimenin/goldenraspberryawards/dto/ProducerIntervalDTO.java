package io.github.iurimenin.goldenraspberryawards.dto;

public record ProducerIntervalDTO(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {
}