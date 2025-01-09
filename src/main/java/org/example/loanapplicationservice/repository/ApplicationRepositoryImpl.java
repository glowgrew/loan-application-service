package org.example.loanapplicationservice.repository;

import org.example.loanapplicationservice.entity.Application;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ApplicationRepositoryImpl implements ApplicationRepository {

    @Override
    public Optional<Application> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public int updateStatus(Long id, String status) {
        return 1;
    }

    @Override
    public Application save(Application application) {
        return application;
    }

}
