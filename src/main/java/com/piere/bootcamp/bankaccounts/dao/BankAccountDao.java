package com.piere.bootcamp.bankaccounts.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.bankaccounts.model.document.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountDao extends ReactiveMongoRepository<BankAccount, String> {

    Mono<BankAccount>  findByAccountCode(String accountCode);

    Flux<BankAccount> findByClientId(String clientId);
}
