package com.charter.rewardPoints.dto;


import lombok.*;

@Data
@AllArgsConstructor
public class MonthlyReward {

    private int year;
    private String month;
    private int points;

}
