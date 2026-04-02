package com.charter.rewardPoints.dto;

import java.time.YearMonth;
import java.util.Map;

public record RewardResponse(
        int customerId,
        Map<YearMonth, Integer> monthlyRewards,
        int totalPoints
){}