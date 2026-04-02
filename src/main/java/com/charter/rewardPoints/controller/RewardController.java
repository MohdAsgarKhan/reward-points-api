package com.charter.rewardPoints.controller;

import com.charter.rewardPoints.dto.RewardResponse;
import com.charter.rewardPoints.service.RewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<List<RewardResponse>> calculateRewards(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        return ResponseEntity.ok(
                rewardService.calculateRewards(startDate, endDate)
        );
    }
}