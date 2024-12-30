package com.piere.bootcamp.bankaccounts.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.bankaccounts.model.document.Movement;
import com.piere.bootcamp.bankaccounts.model.enums.MovementTypeEnum;

import reactor.core.publisher.Flux;


public interface MovementDao extends ReactiveMongoRepository<Movement, String> {

    Flux<Movement> findByIdInAndMovementTypeAndCreateAtBetween(List<String> movementIds, MovementTypeEnum movementType, LocalDate startDate, LocalDate endDate);
    
    Flux<Movement> findByIdInAndCreateAtBetween(List<String> movementIds, LocalDate startDate, LocalDate endDate);
}
