package com.piere.bootcamp.bankaccounts.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.bankaccounts.model.document.DebitCard;

public interface DebitCardDao extends ReactiveMongoRepository<DebitCard, String> {
    
}
