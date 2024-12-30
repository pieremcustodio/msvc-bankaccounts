package com.piere.bootcamp.bankaccounts.model.dto.projection;

import java.io.Serializable;

import com.piere.bootcamp.bankaccounts.model.dto.BankAccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportProjection implements Serializable {
  
    private static final long serialVersionUID = 1L;

    private BankAccountDto bankAccount;
    private Double amount;
}
