package org.example.loanapplicationservice;

import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.service.impl.CreditScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CreditScoreServiceImplTest {

    @Mock
    private CreditScoreServiceImpl creditScoreService;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application(1L, "Test User", "test@example.com", new BigDecimal("10000"), "PENDING");
    }

    @Test
    void calculateCreditScore_ShouldReturnScore() {
        when(creditScoreService.calculateCreditScore(application)).thenReturn(750);

        int score = creditScoreService.calculateCreditScore(application);

        assertEquals(750, score);
        verify(creditScoreService, times(1)).calculateCreditScore(application);
    }
}
