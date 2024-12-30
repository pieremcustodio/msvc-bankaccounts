package com.piere.bootcamp.bankaccounts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piere.bootcamp.bankaccounts.model.dto.OperationDto;
import com.piere.bootcamp.bankaccounts.service.TransferService;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    
    @Autowired
    private TransferService transferService;

    @PostMapping(
        path = "/internal",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<Void>> transferInternal(@Valid @RequestBody OperationDto operationDto) {
        return transferService.transferInternal(operationDto)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
    
}
