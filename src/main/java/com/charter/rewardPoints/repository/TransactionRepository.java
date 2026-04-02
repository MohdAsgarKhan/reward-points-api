package com.charter.rewardPoints.repository;

import com.charter.rewardPoints.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {

    public List<Transaction> getTransaction() {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 101, new BigDecimal("120"), LocalDate.of(2026, 1, 10)));
        transactions.add(new Transaction(2, 101, new BigDecimal("75"), LocalDate.of(2026, 2, 12)));
        transactions.add(new Transaction(3, 101, new BigDecimal("200"), LocalDate.of(2026, 3, 10)));

        transactions.add(new Transaction(4, 102, new BigDecimal("90"), LocalDate.of(2026, 1, 15)));
        transactions.add(new Transaction(5, 102, new BigDecimal("130"), LocalDate.of(2026, 2, 20)));

        return transactions;
    }
}