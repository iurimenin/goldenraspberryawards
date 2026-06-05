package io.github.iurimenin.goldenraspberryawards.controller;

import io.github.iurimenin.goldenraspberryawards.dto.AwardIntervalsResponseDTO;
import io.github.iurimenin.goldenraspberryawards.service.AwardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/awards")
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping("/intervals")
    public ResponseEntity<AwardIntervalsResponseDTO> getAwardIntervals() {
        return ResponseEntity.ok(awardService.getAwardIntervals());
    }
}
