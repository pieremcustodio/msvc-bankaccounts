package com.piere.bootcamp.bankaccounts.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.bankaccounts.model.document.BankAccount;

public interface BankAccountDao extends ReactiveMongoRepository<BankAccount, String> {


}
