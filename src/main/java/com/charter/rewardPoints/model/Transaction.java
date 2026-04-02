package com.charter.rewardPoints.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {

    private int transactionId;
    private int customerId;
    private BigDecimal amount;
    private LocalDate transactionDate;


}