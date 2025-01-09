package org.example.loanapplicationservice.repository;

import org.example.loanapplicationservice.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository {

    Optional<Application> findById(Long id);

    int updateStatus(Long id, String status);

    Application save(Application application);

}
