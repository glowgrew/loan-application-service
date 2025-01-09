package org.example.loanapplicationservice;

import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private Application application;

    @Test
    void sendApplicationStatusUpdate_ShouldReturnTrue() {
        when(application.getEmail()).thenReturn("test@example.com");

        boolean result = notificationService.sendApplicationStatusUpdate(application, "APPROVED");

        assertTrue(result);
        verify(application, times(1)).getEmail();
    }
}
