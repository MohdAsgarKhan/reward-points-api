package com.charter.rewardPoints;

import com.charter.rewardPoints.dto.RewardResponse;
import com.charter.rewardPoints.model.Transaction;
import com.charter.rewardPoints.repository.TransactionRepository;
import com.charter.rewardPoints.service.RewardServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardServiceImplTest {


    private int rewardServiceTestHelper(BigDecimal amount) {

        TransactionRepository repo = new TransactionRepository() {
            @Override
            public List<Transaction> getTransaction() {
                return List.of(
                        new Transaction(
                                1,
                                101,
                                amount, //
                                LocalDate.of(2026, 1, 10)
                        )
                );
            }
        };

        RewardServiceImpl service = new RewardServiceImpl(repo);

        List<RewardResponse> response = service.calculateRewards(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );

        return response.get(0).totalPoints(); //
    }

    @Test
    void testAmountExactly50() {
        assertEquals(0, rewardServiceTestHelper(new BigDecimal("50")));
    }

    @Test
    void testAmountExactly100() {
        assertEquals(50, rewardServiceTestHelper(new BigDecimal("100")));
    }

    @Test
    void testAmountZero() {
        assertEquals(0, rewardServiceTestHelper(new BigDecimal("0")));
    }

    @Test
    void testNegativeAmount() {
        assertEquals(0, rewardServiceTestHelper(new BigDecimal("-10")));
    }

    @Test
    void testAmount120() {
        // 50 (for 50-100) + 40 (for 100-120 *2) = 90
        assertEquals(90, rewardServiceTestHelper(new BigDecimal("120")));
    }
}