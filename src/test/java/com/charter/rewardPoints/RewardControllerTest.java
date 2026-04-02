package com.charter.rewardPoints;

import com.charter.rewardPoints.controller.RewardController;
import com.charter.rewardPoints.exception.ResourceNotFoundException;
import com.charter.rewardPoints.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RewardController.class)
@Import(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RewardService rewardService;

    //Default date handling (null case)
    @Test
    void testNullDates_DefaultsApplied() throws Exception {

        Mockito.when(rewardService.calculateRewards(
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class)
        )).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/rewards/calculate"))
                .andExpect(status().isOk());

        verify(rewardService).calculateRewards(
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class)
        );
    }

    // Success scenario
    @Test
    void testSuccessfulResponse() throws Exception {

        Mockito.when(rewardService.calculateRewards(
                Mockito.any(), Mockito.any()
        )).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/rewards/calculate")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-01"))
                .andExpect(status().isOk());
    }

    // Exception scenario (GlobalExceptionHandler)
    @Test
    void testResourceNotFoundException() throws Exception {

        Mockito.when(rewardService.calculateRewards(
                Mockito.any(), Mockito.any()
        )).thenThrow(new ResourceNotFoundException("No data"));

        mockMvc.perform(get("/api/v1/rewards/calculate")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-01"))
                .andExpect(status().isNotFound());
    }
}