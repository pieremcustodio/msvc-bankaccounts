package com.piere.bootcamp.bankaccounts.service;


import com.piere.bootcamp.bankaccounts.model.dto.BankAccountDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {
    
    Mono<BankAccountDto> create(BankAccountDto bankAccountDto);

    Mono<BankAccountDto> update(BankAccountDto bankAccountDto);

    Mono<Void> delete(BankAccountDto bankAccountDto);

    Mono<BankAccountDto> findById(String id);
    
    Mono<BankAccountDto> deposit(String id, double amount);
    
    Mono<BankAccountDto> withdraw(String id, double amount);
    
    Mono<BankAccountDto> checkBalance(String id);
    
    Flux<BankAccountDto> findAll();
}
