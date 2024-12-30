package com.piere.bootcamp.bankaccounts.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OperationDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String fromAccountCode;

    private String toAccountCode;

    private Double amount;
}
