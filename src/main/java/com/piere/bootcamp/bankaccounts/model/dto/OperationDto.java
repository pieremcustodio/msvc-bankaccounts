package com.piere.bootcamp.bankaccounts.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class OperationDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String fromAccountCode;

    private String toAccountCode;

    private Double amount;

    public OperationDto(Double amount) {
        this.amount = amount;
    }
} 
