package com.piere.bootcamp.bankaccounts.service;

import java.time.LocalDate;
import java.util.List;

import com.piere.bootcamp.bankaccounts.model.dto.projection.ReportProjection;

import reactor.core.publisher.Mono;

public interface ReportService {
    
    Mono<List<ReportProjection>> calculeCommisionByProduct(String clientId,  LocalDate startDate, LocalDate endDate);

    Mono<List<ReportProjection>> calculateAverageDailyBalance(String clientId, LocalDate startDate, LocalDate endDate);
}
