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

import com.piere.bootcamp.bankaccounts.model.dto.BankAccountDto;
import com.piere.bootcamp.bankaccounts.model.dto.OperationDto;
import com.piere.bootcamp.bankaccounts.service.BankAccountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService; 

    /**
     * GET /api/bankaccounts/checkbalance/{id} : Check balance of a bank account
     * Check balance of a bank account
     *
     * @param id ID of the bank account (required)
     * @return Balance consultado (status code 200)
     */
    @ApiOperation(value = "Check balance of a bank account", nickname = "checkBalance", notes = "Check balance of a bank account",  tags={ "bankaccounts"})
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Balance consultado") })
    @GetMapping(
        value = "/checkbalance/{id}",
        produces = { "application/json" }
    )
    Mono<ResponseEntity<BankAccountDto>> checkBalance(@ApiParam(value = "ID of the bank account",required=true) @PathVariable("id") String id) {
        return bankAccountService.checkBalance(id)
                .map(bankAccount -> ResponseEntity.ok().body(bankAccount));
    }
 
    /**
     * POST /api/bankaccounts : Opening an account bank
     * Opening a new account bank
     *
     * @param bankAccountDto  (required)
     * @return Account bank created (status code 201)
     *         or Bad request (status code 400)
     *         or already exists (status code 409)
     */
    @ApiOperation(value = "Opening an account bank", nickname = "create", notes = "Opening a new account bank", response = BankAccountDto.class, tags={ "bankaccounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Account bank created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 409, message = "already exists") })
    @PostMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<BankAccountDto>> createBankAccount(@ApiParam(value = "" ,required=true )  @Valid @RequestBody BankAccountDto bankAccountDto) {
        return bankAccountService.create(bankAccountDto)
                .map(bankAccount -> ResponseEntity.created(URI.create("/api/bankaccounts")).body(bankAccount));
    }

    /**
     * DELETE /api/bankaccounts : Delete an account bank
     * Delete an existing account bank
     *
     * @param bankAccountDto  (required)
     * @return Account bank deleted (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Delete an account bank", nickname = "delete", notes = "Delete an existing account bank", response = BankAccountDto.class, tags={ "bankaccounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Account bank deleted"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @DeleteMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<Void>> delete(@ApiParam(value = "" ,required=true )  @Valid @RequestBody BankAccountDto bankAccountDto) {
        return bankAccountService.delete(bankAccountDto)
                .map(bankAccount -> ResponseEntity.ok().build());
    }

    /**
     * PUT /api/bankaccounts : Update an account bank
     * Update an existing account bank
     *
     * @param bankAccountDto  (required)
     * @return Account bank updated (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Update an account bank", nickname = "update", notes = "Update an existing account bank", response = BankAccountDto.class, tags={ "bankaccounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Account bank updated", response = BankAccountDto.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @PutMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<BankAccountDto>> update(@ApiParam(value = "" ,required=true )  @Valid @RequestBody BankAccountDto bankAccountDto) {
        return bankAccountService.update(bankAccountDto)
                .map(bankAccount -> ResponseEntity.ok().body(bankAccount));
    }

    /**
     * POST /api/bankaccounts/withdraw/{id} : Withdraw from a bank account
     * Withdraw from a bank account
     *
     * @param id ID of the bank account (required)
     * @param operationDto  (required)
     * @return Retiro realizado (status code 200)
     */
    @ApiOperation(value = "Withdraw from a bank account", nickname = "withdraw", notes = "Withdraw from a bank account", response = BankAccountDto.class, tags={ "bankaccounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Retiro realizado", response = BankAccountDto.class) })
    @PostMapping(
        value = "/withdraw/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<BankAccountDto>> withdraw(@ApiParam(value = "ID of the bank account",required=true) @PathVariable("id") String id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody OperationDto operationDto) {
        return bankAccountService.withdraw(id, operationDto.getAmount())
                .map(bankAccount -> ResponseEntity.ok().body(bankAccount));
    }

    /**
     * POST /api/bankaccounts/deposit/{id} : Deposit to a bank account
     * Deposit to a bank account
     *
     * @param id Id of the bank account (required)
     * @param operationDto  (required)
     * @return Depósito realizado (status code 200)
     */
    @ApiOperation(value = "Deposit to a bank account", nickname = "deposit", notes = "Deposit to a bank account", response = BankAccountDto.class, tags={ "bankaccounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Depósito realizado", response = BankAccountDto.class) })
    @PostMapping(
        value = "/deposit/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<BankAccountDto>> deposit(@ApiParam(value = "Id of the bank account",required=true) @PathVariable("id") String id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody OperationDto operationDto) {
        return bankAccountService.deposit(id, operationDto.getAmount())
                .map(bankAccount -> ResponseEntity.ok().body(bankAccount));
    }

    /**
     * GET /api/bankaccounts : Get all account banks
     * Use to request all account banks
     *
     * @return A list of account banks (status code 200)
     */
    @ApiOperation(value = "Get all account banks", nickname = "findAllBankAccounts", notes = "Use to request all account banks", response = BankAccountDto.class, responseContainer = "List", tags={ "bankaccounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of account banks", response = BankAccountDto.class, responseContainer = "List") })
    @GetMapping(
        produces = { "application/json" }
    )
    Mono<ResponseEntity<Flux<BankAccountDto>>> findAllBankAccounts() {
        return Mono.just(ResponseEntity.ok(bankAccountService.findAll()));
    }
}
