package com.piere.bootcamp.bankaccounts.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.bankaccounts.model.document.Movement;


public interface MovementDao extends ReactiveMongoRepository<Movement, String> {

}
