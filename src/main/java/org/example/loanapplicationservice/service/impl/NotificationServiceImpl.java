package org.example.loanapplicationservice.service.impl;

import org.example.loanapplicationservice.entity.Application;
import org.example.loanapplicationservice.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public boolean sendApplicationStatusUpdate(Application application, String status) {
        System.out.println("Notification sent to: " + application.getEmail());
        return true;
    }

}
