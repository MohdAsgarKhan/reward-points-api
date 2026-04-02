package com.charter.rewardPoints.service;

import com.charter.rewardPoints.dto.RewardResponse;
import com.charter.rewardPoints.exception.DateRangeException;
import com.charter.rewardPoints.exception.ResourceNotFoundException;
import com.charter.rewardPoints.model.Transaction;
import com.charter.rewardPoints.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class RewardServiceImpl implements RewardService {

    private final TransactionRepository transactionRepository;

    public RewardServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<RewardResponse> calculateRewards(LocalDate startDate, LocalDate endDate) {

        // ✅ Step 1: Use temp variables (modifiable)
        LocalDate tempStartDate = startDate;
        LocalDate tempEndDate = endDate;

        // ✅ Default: last 3 months
        if (tempStartDate == null && tempEndDate == null) {
            tempEndDate = LocalDate.now();
            tempStartDate = tempEndDate.minusMonths(3);
        }

        // ✅ Validation
        if (tempStartDate == null || tempEndDate == null) {
            throw new DateRangeException("Both startDate and endDate must be provided");
        }

        if (tempStartDate.isAfter(tempEndDate)) {
            throw new DateRangeException("Start date cannot be after end date");
        }

        // ✅ Strict 3-month rule
        if (tempStartDate.plusMonths(3).isBefore(tempEndDate)) {
            throw new DateRangeException("Date range cannot exceed 3 months");
        }

        // ✅ Step 2: FINAL variables (fix lambda issue)
        final LocalDate resolvedStartDate = tempStartDate;
        final LocalDate resolvedEndDate = tempEndDate;

        // ✅ Fetch transactions
        List<Transaction> transactions = transactionRepository.getTransaction();

        // ✅ Filter transactions using correct getter
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getTransactionDate() != null) // safety check
                .filter(t -> !t.getTransactionDate().isBefore(resolvedStartDate) &&
                        !t.getTransactionDate().isAfter(resolvedEndDate))
                .toList();

        if (filteredTransactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found for given criteria");
        }

        // ✅ Group by customerId and YearMonth
        Map<Integer, Map<YearMonth, Integer>> customerMonthlyPoints = new HashMap<>();

        for (Transaction t : filteredTransactions) {

            int points = calculatePoints(t.getAmount());

            YearMonth yearMonth = YearMonth.from(t.getTransactionDate());

            customerMonthlyPoints
                    .computeIfAbsent(t.getCustomerId(), k -> new HashMap<>())
                    .merge(yearMonth, points, Integer::sum);
        }

        // ✅ Sort response: customerId → YearMonth
        List<RewardResponse> response = customerMonthlyPoints.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) // customerId sort
                .map(entry -> {

                    Map<YearMonth, Integer> sortedMonthly = entry.getValue()
                            .entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey()) // YearMonth sort
                            .collect(
                                    LinkedHashMap::new,
                                    (m, e) -> m.put(e.getKey(), e.getValue()),
                                    Map::putAll
                            );

                    int totalPoints = sortedMonthly.values()
                            .stream()
                            .mapToInt(Integer::intValue)
                            .sum();

                    return new RewardResponse(
                            entry.getKey(),
                            sortedMonthly,
                            totalPoints
                    );
                })
                .toList();

        return response;
    }

    // ✅ Null-safe reward calculation
    private int calculatePoints(BigDecimal amount) {

        if (amount == null) {
            return 0;
        }

        int amt = amount.intValue();
        int points = 0;

        if (amt > 50) {
            points += Math.min(amt, 100) - 50;
        }

        if (amt > 100) {
            points += (amt - 100) * 2;
        }

        return Math.max(points, 0);
    }
}