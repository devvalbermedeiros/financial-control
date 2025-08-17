package com.valber.financial_control.domain.repository;

import com.valber.financial_control.domain.entity.Revenue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface RevenueRepository extends MongoRepository<Revenue, String> {

    List<Revenue> findByUserId(String userId);
    List<Revenue> findByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate);
}
