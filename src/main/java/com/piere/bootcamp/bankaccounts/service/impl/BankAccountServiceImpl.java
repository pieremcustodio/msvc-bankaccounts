package com.piere.bootcamp.bankaccounts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piere.bootcamp.bankaccounts.dao.BankAccountDao;
import com.piere.bootcamp.bankaccounts.model.dto.BankAccountDto;
import com.piere.bootcamp.bankaccounts.model.dto.MovementDto;
import com.piere.bootcamp.bankaccounts.model.enums.MovementTypeEnum;
import com.piere.bootcamp.bankaccounts.service.BankAccountService;
import com.piere.bootcamp.bankaccounts.service.MovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountDao accountBankDao;

    @Autowired
    private MovementService movementService;

    @Override
    public Mono<BankAccountDto> create(BankAccountDto bankAccountDto) {
        return accountBankDao.save(BankAccountDto.build().toEntity(bankAccountDto))
            .map(BankAccountDto.build()::toDto);
    }

    @Override
    public Mono<BankAccountDto> update(BankAccountDto bankAccountDto) {
        return accountBankDao.save(BankAccountDto.build().toEntity(bankAccountDto))
            .map(BankAccountDto.build()::toDto);
    }

    @Override
    public Mono<Void> delete(BankAccountDto bankAccountDto) {
        return accountBankDao.findById(bankAccountDto.getId())
        .switchIfEmpty(Mono.error(new RuntimeException("Cuenta bancaria no encontrada")))
        .flatMap(account -> {
            if (account.getBalance() > 0) {
                return Mono.error(new RuntimeException("No se puede eliminar la cuenta bancaria porque tiene saldo"));
            }
            return accountBankDao.delete(account);
        });
    }

    @Override
    public Mono<BankAccountDto> checkBalance(String id) {
        return accountBankDao.findById(id)
            .flatMap(account -> {
                return movementService.findAllByIdList(account.getMovementIds())
                    .collectList()
                    .map(movements -> {
                        BankAccountDto dto = BankAccountDto.build().toDto(account);
                        dto.setMovements(movements);
                        return dto;
                    });
            });
    }

    @Override
    public Flux<BankAccountDto> findAll() {
        return accountBankDao.findAll()
            .map(BankAccountDto.build()::toDto);
    }

    @Override
    public Mono<BankAccountDto> deposit(String id, double amount) {
        return accountBankDao.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta bancaria no encontrada")))
                .flatMap(account -> {
                    return movementService.create(MovementDto.builder()
                            .amount(amount)
                            .movementType(MovementTypeEnum.DEPOSITO)
                            .description("Depósito realizado")
                            .build())
                            .flatMap(movement -> {
                                account.setBalance(account.getBalance() + amount);
                                account.getMovementIds().add(movement.getId());
                                return accountBankDao.save(account);
                            });
                })
                .map(BankAccountDto.build()::toDto);
    }

    @Override
    public Mono<BankAccountDto> withdraw(String id, double amount) {
        return accountBankDao.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta bancaria no encontrada")))
                .flatMap(account -> {
                    if (account.getBalance() < amount) {
                        return Mono.error(new RuntimeException("Saldo insuficiente para realizar el retiro"));
                    }
                    return movementService.create(MovementDto.builder()
                            .amount(amount)
                            .movementType(MovementTypeEnum.RETIRO)
                            .description("Retiro realizado")
                            .build())
                            .flatMap(movement -> {
                                account.setBalance(account.getBalance() - amount);
                                account.getMovementIds().add(movement.getId());
                                return accountBankDao.save(account);
                            });
                })
                .map(BankAccountDto.build()::toDto);
    }

    @Override
    public Mono<BankAccountDto> findById(String id) {
        return accountBankDao.findById(id)
            .map(BankAccountDto.build()::toDto);
    }
}
