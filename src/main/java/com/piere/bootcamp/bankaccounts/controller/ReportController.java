package com.piere.bootcamp.bankaccounts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piere.bootcamp.bankaccounts.model.dto.projection.ReportProjection;
import com.piere.bootcamp.bankaccounts.model.dto.search.SearchReportDto;
import com.piere.bootcamp.bankaccounts.service.ReportService;

import reactor.core.publisher.Mono;


@CrossOrigin
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
    @Autowired
    private ReportService reportService;

    @PostMapping(
        path = "/calculeCommisionByProduct",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<List<ReportProjection>> calculeCommisionByProduct(@PathVariable String clientId, @Valid @RequestBody SearchReportDto search) {
        return reportService.calculeCommisionByProduct(search.getClientId(), search.getStartDate(), search.getEndDate())
                .flatMap(Mono::just);
    }

    @PostMapping(
        path = "/calculateAverageDailyBalance",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public Mono<List<ReportProjection>> calculateAverageDailyBalance(@Valid @RequestBody SearchReportDto search) {
        return reportService.calculateAverageDailyBalance(search.getClientId(), search.getStartDate(), search.getEndDate())
                .flatMap(Mono::just);
    }
}
