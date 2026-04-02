package com.charter.rewardPoints.service;

import com.charter.rewardPoints.dto.RewardResponse;
import java.time.LocalDate;
import java.util.List;

public interface RewardService {

    List<RewardResponse> calculateRewards(LocalDate startDate, LocalDate endDate);
}