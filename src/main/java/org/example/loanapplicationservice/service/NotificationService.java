package org.example.loanapplicationservice.service;

import org.example.loanapplicationservice.entity.Application;

public interface NotificationService {

    boolean sendApplicationStatusUpdate(Application application, String status);

}
