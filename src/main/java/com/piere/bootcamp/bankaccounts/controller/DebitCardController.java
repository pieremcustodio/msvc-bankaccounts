package com.piere.bootcamp.bankaccounts.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piere.bootcamp.bankaccounts.model.InlineResponse400;
import com.piere.bootcamp.bankaccounts.model.InlineResponse4001;
import com.piere.bootcamp.bankaccounts.model.InlineResponse404;
import com.piere.bootcamp.bankaccounts.model.InlineResponse409;
import com.piere.bootcamp.bankaccounts.model.dto.DebitCardDto;
import com.piere.bootcamp.bankaccounts.model.dto.MovementDto;
import com.piere.bootcamp.bankaccounts.model.dto.OperationDto;
import com.piere.bootcamp.bankaccounts.service.DebitCardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api/debitcards")
public class DebitCardController {


    @Autowired
    private DebitCardService debitCardService;

    /**
     * GET /api/debitcards : Get all debit cards
     * Use to request all debit cards
     *
     * @return A list of debit cards (status code 200)
     */
    @ApiOperation(value = "Get all debit cards", nickname = "findAllDebitCards", notes = "Use to request all debit cards", 
            response = DebitCardDto.class, responseContainer = "List", tags = {
            "debitcards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of debit cards", response = DebitCardDto.class, responseContainer = "List") })
    @GetMapping(value = "/api/debitcards", produces = { "application/json" })
    public Mono<ResponseEntity<Flux<DebitCardDto>>> findAllDebitCards() {
        return Mono.just(ResponseEntity.ok(debitCardService.findAll()));
    }

    /**
     * POST /api/debitcards : Create a debit card
     * Create a new debit card
     *
     * @param debitCardDto  (required)
     * @return Debit card created (status code 201)
     *         or Bad request (status code 400)
     *         or already exists (status code 409)
     */
    @ApiOperation(value = "Create a debit card", nickname = "createDebitCard", notes = "Create a new debit card", response = DebitCardDto.class, tags = {
            "debitcards", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Debit card created", response = DebitCardDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = InlineResponse400.class),
            @ApiResponse(code = 409, message = "already exists", response = InlineResponse409.class) })
    @PostMapping(value = "/api/debitcards", produces = { "application/json" }, consumes = { "application/json" })
    public Mono<ResponseEntity<DebitCardDto>> createDebitCard(
            @ApiParam(value = "", required = true) @Valid @RequestBody DebitCardDto debitCardDto) {
        return debitCardService.create(debitCardDto)
                .map(debitCard -> ResponseEntity.created(URI.create("/api/debitcards")).body(debitCard));
    }

/**
     * PUT /api/debitcards : Update a debit card
     * Update an existing debit card
     *
     * @param debitCardDto  (required)
     * @return Debit card updated (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Update a debit card", nickname = "updateDebitCard", notes = "Update an existing debit card", response = DebitCardDto.class, tags = {
        "debitcards", })
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Debit card updated", response = DebitCardDto.class),
        @ApiResponse(code = 400, message = "Bad request", response = InlineResponse400.class),
        @ApiResponse(code = 404, message = "Not found", response = InlineResponse404.class) })
@PutMapping(value = "/api/debitcards", produces = { "application/json" }, consumes = { "application/json" })
public Mono<ResponseEntity<DebitCardDto>> updateDebitCard(
        @ApiParam(value = "", required = true) @Valid @RequestBody DebitCardDto debitCardDto) {
    return debitCardService.update(debitCardDto)
            .map(debitCard -> ResponseEntity.ok().body(debitCard));
}
    
    /**
     * DELETE /api/debitcards : Delete a debit card
     * Delete an existing debit card
     *
     * @param debitCardDto  (required)
     * @return Debit card deleted (status code 200)
     *         or Bad request (status code 400)
     */
    @ApiOperation(value = "Delete a debit card", nickname = "deleteDebitCard", notes = "Delete an existing debit card", response = DebitCardDto.class, tags = {
            "debitcards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Debit card deleted", response = DebitCardDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = InlineResponse400.class) })
    @DeleteMapping(value = "/api/debitcards", produces = { "application/json" }, consumes = { "application/json" })
    public Mono<ResponseEntity<Void>> deleteDebitCard(
            @ApiParam(value = "", required = true) @Valid @RequestBody DebitCardDto debitCardDto) {
        return debitCardService.delete(debitCardDto)
                .map(debitCard -> ResponseEntity.ok().build());
    }

    /**
     * GET /api/debitcards/checkbalance/{id} : Check balance of a debit card
     * Check balance of a debit card
     *
     * @param id ID of the debit card (required)
     * @return Balance consultado (status code 200)
     */
    @ApiOperation(value = "Check balance of a debit card", nickname = "checkBalanceDebitCard", notes = "Check balance of a debit card", 
    response = DebitCardDto.class, tags = {"debitcards"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Balance consultado", response = DebitCardDto.class) })
    @GetMapping(value = "/api/debitcards/checkbalance/{id}", produces = { "application/json" })
    public Mono<ResponseEntity<OperationDto>> checkBalanceDebitCard(
            @ApiParam(value = "ID of the debit card", required = true) @PathVariable String id) {
        return debitCardService.checkBalance(id)
                .map(debitCard -> ResponseEntity.ok().body(debitCard));
    }

    /**
     * POST /api/debitcards/withdraw/{id} : Withdraw from a debit card
     * Withdraw from a debit card
     *
     * @param id ID of the debit card (required)
     * @param operationDto  (required)
     * @return Retiro realizado (status code 200)
     *         or Insuficient funds (status code 400)
     */
    @ApiOperation(value = "Withdraw from a debit card", nickname = "withdrawDebitCard", notes = "Withdraw from a debit card", 
        response = DebitCardDto.class, tags = { "debitcards", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Retiro realizado", response = DebitCardDto.class),
        @ApiResponse(code = 400, message = "Insuficient funds", response = InlineResponse4001.class) })
    @PostMapping(
        value = "/api/debitcards/withdraw/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public Mono<ResponseEntity<DebitCardDto>> withdrawDebitCard(@ApiParam(value = "ID of the debit card", required=true) @PathVariable("id") String id,@ApiParam(value = "", required = true)  @Valid @RequestBody Mono<OperationDto> operationDto) {
        return debitCardService.withdraw(id, 0)
                .map(debitCard -> ResponseEntity.ok().body(debitCard));
    }

    /**
     * POST /api/debitcards/payment/{id} : Payment with a debit card
     * Payment with a debit card
     *
     * @param id ID of the debit card (required)
     * @param operationDto  (required)
     * @return Pago realizado (status code 200)
     *         or Insuficient funds (status code 400)
     */
    @ApiOperation(value = "Payment with a debit card", nickname = "payment", notes = "Payment with a debit card", 
    response = DebitCardDto.class, tags = {"debitcards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pago realizado", response = DebitCardDto.class),
            @ApiResponse(code = 400, message = "Insuficient funds", response = InlineResponse4001.class) })
    @PostMapping(value = "/api/debitcards/payment/{id}", produces = { "application/json" }, consumes = {
            "application/json" })
    public Mono<ResponseEntity<DebitCardDto>> payment(
            @ApiParam(value = "ID of the debit card", required = true) @PathVariable("id") String id,
            @ApiParam(value = "", required = true) @Valid @RequestBody Mono<OperationDto> operationDto) {
        return null;
    }

    /**
     * GET /api/debitcards/findLast10Movements/{id} : Find last 10 movements of a debit card
     * Find last 10 movements of a debit card
     *
     * @param id ID of the debit card (required)
     * @return Movements consulted (status code 200)
     */
    @ApiOperation(value = "Find last ten movements of a debit card", nickname = "findLastTenMovements", notes = "Find last ten movements of a debit card", 
        response = MovementDto.class, responseContainer = "List", tags = { "debitcards", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Movements consulted", response = MovementDto.class, responseContainer = "List") })
    @GetMapping(
        value = "/api/debitcards/findLastTenMovements/{id}",
        produces = { "application/json" }
    )
    public Mono<ResponseEntity<Flux<MovementDto>>> findLastTenMovements(@ApiParam(value = "ID of the debit card", required=true) @PathVariable String id) {
        return Mono.just(ResponseEntity.ok(debitCardService.findLastTenMovements(id)));
    }
    
}
