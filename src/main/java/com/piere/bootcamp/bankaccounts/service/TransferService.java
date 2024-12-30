package com.piere.bootcamp.bankaccounts.service;


import com.piere.bootcamp.bankaccounts.model.dto.OperationDto;

import reactor.core.publisher.Mono;

public interface TransferService {
    
    Mono<Void> transferInternal(OperationDto operationDto);
}
