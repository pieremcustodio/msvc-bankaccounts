package com.piere.bootcamp.bankaccounts.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piere.bootcamp.bankaccounts.dao.DebitCardDao;
import com.piere.bootcamp.bankaccounts.model.dto.BankAccountDto;
import com.piere.bootcamp.bankaccounts.model.dto.DebitCardDto;
import com.piere.bootcamp.bankaccounts.model.dto.MovementDto;
import com.piere.bootcamp.bankaccounts.model.dto.OperationDto;
import com.piere.bootcamp.bankaccounts.model.enums.MovementTypeEnum;
import com.piere.bootcamp.bankaccounts.service.BankAccountService;
import com.piere.bootcamp.bankaccounts.service.DebitCardService;
import com.piere.bootcamp.bankaccounts.service.MovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DebitCardServiceIml implements DebitCardService {

    @Autowired
    private DebitCardDao debitCardDao;

    @Autowired
    private MovementService movementService;

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    public Mono<DebitCardDto> create(DebitCardDto debitCardDto) {
        return debitCardDao.save(DebitCardDto.build().toEntity(debitCardDto))
            .map(DebitCardDto.build()::toDto);
    }

    @Override
    public Mono<DebitCardDto> update(DebitCardDto debitCardDto) {
        return debitCardDao.save(DebitCardDto.build().toEntity(debitCardDto))
            .map(DebitCardDto.build()::toDto);
    }

    @Override
    public Mono<Void> delete(DebitCardDto debitCardDto) {
        return debitCardDao.delete(DebitCardDto.build().toEntity(debitCardDto));
    }

    @Override
    public Mono<DebitCardDto> withdraw(String id, double amount) {
        return debitCardDao.findById(id)
                .flatMap(debitCard -> {
                    List<String> accountIds = new ArrayList<>();
                    accountIds.add(debitCard.getMainAccountId());
                    accountIds.addAll(debitCard.getAssociatedAccountIds());

                    return bankAccountService.findAllByIdList(accountIds)
                            .collectList()
                            .flatMap(bankAccounts -> {
                                double remainingAmount = amount;

                                for (BankAccountDto account : bankAccounts) {
                                    if (account.getBalance() >= remainingAmount) {
                                        account.setBalance(account.getBalance() - remainingAmount);
                                        return movementService.create(MovementDto.builder()
                                                .amount(remainingAmount)
                                                .createAt(LocalDate.now())
                                                .description("Retiro con tarjeta de debito")
                                                .movementType(MovementTypeEnum.RETIRO)
                                                .build())
                                                .flatMap(movement -> {
                                                    return bankAccountService.update(account)
                                                            .map(savedAccount -> {
                                                                return DebitCardDto.builder()
                                                                        .id(debitCard.getId())
                                                                        .clientId(debitCard.getClientId())
                                                                        .mainAccountId(debitCard.getMainAccountId())
                                                                        .associatedAccountIds(
                                                                                debitCard.getAssociatedAccountIds())
                                                                        .expirationDate(debitCard.getExpirationDate())
                                                                        .build();
                                                            });
                                                });
                                    }
                                }

                                return Mono.error(new RuntimeException("Saldo insuficiente para realizar el pago"));
                            });
                });
    }

    @Override
    public Mono<OperationDto> checkBalance(String id) {
        return debitCardDao.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Tarjeta de debito no encontrada")))
            .flatMap(debitCard -> {
                return bankAccountService.findById(debitCard.getMainAccountId())
                    .map(bankAccount -> {
                        return new OperationDto(bankAccount.getBalance());
                    });
            });
    }

    @Override
    public Mono<DebitCardDto> payment(String id, double amount) {
        return debitCardDao.findById(id)
                .flatMap(debitCard -> {
                    List<String> accountIds = new ArrayList<>();
                    accountIds.add(debitCard.getMainAccountId());
                    accountIds.addAll(debitCard.getAssociatedAccountIds());

                    return bankAccountService.findAllByIdList(accountIds)
                            .collectList()
                            .flatMap(bankAccounts -> {
                                double remainingAmount = amount;

                                for (BankAccountDto account : bankAccounts) {
                                    if (account.getBalance() >= remainingAmount) {
                                        account.setBalance(account.getBalance() - remainingAmount);
                                        return movementService.create(MovementDto.builder()
                                                .amount(remainingAmount)
                                                .createAt(LocalDate.now())
                                                .description("Pago con tarjeta de debito")
                                                .movementType(MovementTypeEnum.TRANSFERENCIA)
                                                .build())
                                                .flatMap(movement -> {
                                                    return bankAccountService.update(account)
                                                            .map(savedAccount -> {
                                                                return DebitCardDto.builder()
                                                                        .id(debitCard.getId())
                                                                        .clientId(debitCard.getClientId())
                                                                        .mainAccountId(debitCard.getMainAccountId())
                                                                        .associatedAccountIds(
                                                                                debitCard.getAssociatedAccountIds())
                                                                        .expirationDate(debitCard.getExpirationDate())
                                                                        .build();
                                                            });
                                                });
                                    }
                                }

                                return Mono.error(new RuntimeException("Saldo insuficiente para realizar el pago"));
                            });
                });
    }

    @Override
    public Flux<DebitCardDto> findAll() {
        return debitCardDao.findAll()
            .map(DebitCardDto.build()::toDto);
    }

    @Override
    public Flux<MovementDto> findLastTenMovements(String id) {
        return debitCardDao.findById(id)
                .flatMapMany(debitCard -> {
                    return movementService.findAllByIdList(debitCard.getMovementIds())
                            .sort(Comparator.comparing(MovementDto::getCreateAt).reversed())
                            .take(10);
                });
    }

    
}
