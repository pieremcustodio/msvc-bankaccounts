package com.piere.bootcamp.bankaccounts.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piere.bootcamp.bankaccounts.dao.BankAccountDao;
import com.piere.bootcamp.bankaccounts.dao.MovementDao;
import com.piere.bootcamp.bankaccounts.model.document.Movement;
import com.piere.bootcamp.bankaccounts.model.dto.OperationDto;
import com.piere.bootcamp.bankaccounts.model.enums.MovementTypeEnum;
import com.piere.bootcamp.bankaccounts.service.TransferService;

import reactor.core.publisher.Mono;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private BankAccountDao bankAccountDao;

    @Autowired
    private MovementDao movementDao;

    @Override
    public Mono<Void> transferInternal(OperationDto operationDto) {
        return bankAccountDao.findByAccountCode(operationDto.getFromAccountCode())
                .flatMap(fromAccount -> {
                    if (fromAccount.getBalance() < operationDto.getAmount()) {
                        return Mono.error(new RuntimeException("Saldo insuficiente en la cuenta de origen"));
                    }

                    fromAccount.setBalance(fromAccount.getBalance() - operationDto.getAmount());

                    return bankAccountDao.findByAccountCode(operationDto.getToAccountCode())
                            .flatMap(toAccount -> {
                                toAccount.setBalance(toAccount.getBalance() + operationDto.getAmount());

                                Movement fromAccountMovement = Movement.builder()
                                        .amount(operationDto.getAmount())
                                        .createAt(LocalDate.now())
                                        .movementType(MovementTypeEnum.TRANSFERENCIA)
                                        .description("Transferencia realizada")
                                        .build();
                                Movement toAccountMovement = Movement.builder()
                                        .amount(operationDto.getAmount())
                                        .createAt(LocalDate.now())
                                        .movementType(MovementTypeEnum.TRANSFERENCIA)
                                        .description("Transferencia recibida")
                                        .build();

                                Mono<Void> fromAccountMovementSave = movementDao.save(fromAccountMovement).then();
                                Mono<Void> toAccountMovementSave = movementDao.save(toAccountMovement).then();

                                fromAccount.getMovementIds().add(fromAccountMovement.getId());
                                toAccount.getMovementIds().add(toAccountMovement.getId());

                                Mono<Void> saveFromAccount = bankAccountDao.save(fromAccount).then();
                                Mono<Void> saveToAccount = bankAccountDao.save(toAccount).then();

                                return Mono
                                        .zip(fromAccountMovementSave, toAccountMovementSave, saveFromAccount,
                                                saveToAccount)
                                        .then();
                            });
                });
    }
    
}
