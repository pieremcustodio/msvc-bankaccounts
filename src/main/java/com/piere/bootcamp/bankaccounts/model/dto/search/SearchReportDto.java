package com.piere.bootcamp.bankaccounts.model.dto.search;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchReportDto implements Serializable {
  
    private static final long serialVersionUID = 1L;

    private String clientId;
    private LocalDate startDate;
    private LocalDate endDate;
}
