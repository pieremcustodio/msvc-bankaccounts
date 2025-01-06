package com.piere.bootcamp.bankaccounts.service;


import com.piere.bootcamp.bankaccounts.model.dto.DebitCardDto;
import com.piere.bootcamp.bankaccounts.model.dto.MovementDto;
import com.piere.bootcamp.bankaccounts.model.dto.OperationDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DebitCardService {
    
    Mono<DebitCardDto> create(DebitCardDto debitCardDto);

    Mono<DebitCardDto> update(DebitCardDto debitCardDto);

    Mono<Void> delete(DebitCardDto debitCardDto);

    Mono<DebitCardDto> withdraw(String id, double amount);

    Mono<OperationDto> checkBalance(String id);

    Mono<DebitCardDto> payment(String id, double amount);

    Flux<DebitCardDto> findAll();

    Flux<MovementDto> findLastTenMovements(String id);
}
