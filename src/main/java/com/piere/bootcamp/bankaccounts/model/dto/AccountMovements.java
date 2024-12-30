package com.piere.bootcamp.bankaccounts.model.dto;

import java.util.List;

import com.piere.bootcamp.bankaccounts.model.document.BankAccount;
import com.piere.bootcamp.bankaccounts.model.document.Movement;

import lombok.Data;

@Data
public class AccountMovements {
    private BankAccount account;
    private List<Movement> movements;

    public AccountMovements(BankAccount account, List<Movement> movements) {
        this.account = account;
        this.movements = movements;
    }
}
