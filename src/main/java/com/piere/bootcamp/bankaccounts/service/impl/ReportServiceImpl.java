package com.piere.bootcamp.bankaccounts.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.piere.bootcamp.bankaccounts.dao.BankAccountDao;
import com.piere.bootcamp.bankaccounts.dao.MovementDao;
import com.piere.bootcamp.bankaccounts.model.document.BankAccount;
import com.piere.bootcamp.bankaccounts.model.document.Movement;
import com.piere.bootcamp.bankaccounts.model.dto.BankAccountDto;
import com.piere.bootcamp.bankaccounts.model.dto.projection.ReportProjection;
import com.piere.bootcamp.bankaccounts.model.enums.MovementTypeEnum;
import com.piere.bootcamp.bankaccounts.service.ReportService;

import reactor.core.publisher.Mono;

public class ReportServiceImpl implements ReportService {

    @Autowired
    private BankAccountDao bankAccountDao;

    @Autowired
    private MovementDao movementDao;

    @Override
    public Mono<List<ReportProjection>> calculeCommisionByProduct(String clientId, LocalDate startDate,
            LocalDate endDate) {
        return bankAccountDao.findByClientId(clientId)
                .flatMap(account -> movementDao.findByIdInAndMovementTypeAndCreateAtBetween(
                        account.getMovementIds(),
                        MovementTypeEnum.COMISION,
                        startDate,
                        endDate)
                        .collectList()
                        .map(movements -> groupCommissionsByProduct(account, movements)))
                .collectList();
    }

    @Override
    public Mono<List<ReportProjection>> calculateAverageDailyBalance(String clientId, LocalDate startDate,
            LocalDate endDate) {
        return bankAccountDao.findByClientId(clientId)
                .flatMap(account -> movementDao.findByIdInAndCreateAtBetween(
                        account.getMovementIds(),
                        startDate,
                        endDate)
                        .collectList()
                        .map(movements -> calculateDailyBalance(account, movements, startDate, endDate)))
                .collectList();
    }

    private ReportProjection calculateDailyBalance(BankAccount account, List<Movement> movements, LocalDate startDate,
            LocalDate endDate) {
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        double totalBalance = movements.stream()
                .mapToDouble(Movement::getAmount)
                .sum();

        double averageBalance = totalBalance / totalDays;

        return new ReportProjection(
                BankAccountDto.build().toDto(account),
                averageBalance);
    }

    private ReportProjection groupCommissionsByProduct(BankAccount account, List<Movement> movements) {
        Double totalCommision = movements.stream()
                .mapToDouble(Movement::getAmount)
                .sum();

        return new ReportProjection(
                BankAccountDto.build().toDto(account),
                totalCommision);
    }
}
