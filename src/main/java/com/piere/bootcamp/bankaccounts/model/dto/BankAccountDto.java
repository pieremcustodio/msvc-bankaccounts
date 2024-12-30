package com.piere.bootcamp.bankaccounts.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.piere.bootcamp.bankaccounts.model.document.BankAccount;
import com.piere.bootcamp.bankaccounts.model.enums.BankAccountTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * BankAccountDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankAccountDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String accountCode;

  private BankAccountTypeEnum bankAccountType;

  private Double balance;

  private String clientId;

  @ApiModelProperty(hidden = true)
  @ToString.Exclude
  @Builder.Default
  private List<MovementDto> movements = new ArrayList();

  private List<String> movementIds;

  private Integer limitMovement;

  private Double maintenance;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate createAt;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate endAt;

  private Boolean status;

  public static BankAccountDto build() {
    return BankAccountDto.builder().build();
  }

  public BankAccountDto toDto(BankAccount bankAccount) {
    return BankAccountDto.builder()
      .id(bankAccount.getId())
      .accountCode(bankAccount.getAccountCode())
      .bankAccountType(bankAccount.getBankAccountType())
      .balance(bankAccount.getBalance())
      .clientId(bankAccount.getClientId())
      .movementIds(bankAccount.getMovementIds())
      .limitMovement(bankAccount.getLimitMovement())
      .maintenance(bankAccount.getMaintenance())
      .createAt(bankAccount.getCreateAt())
      .endAt(bankAccount.getEndAt())
      .status(bankAccount.getStatus())
      .build();
  }
  
  public BankAccount toEntity(BankAccountDto bankAccountDto) {
    return BankAccount.builder()
      .id(bankAccountDto.getId())
      .accountCode(bankAccountDto.getAccountCode())
      .bankAccountType(bankAccountDto.getBankAccountType())
      .balance(bankAccountDto.getBalance())
      .clientId(bankAccountDto.getClientId())
      .movementIds(bankAccountDto.getMovements().stream().map(MovementDto::getId).collect(Collectors.toList()))
      .limitMovement(bankAccountDto.getLimitMovement())
      .maintenance(bankAccountDto.getMaintenance())
      .createAt(bankAccountDto.getCreateAt())
      .endAt(bankAccountDto.getEndAt())
      .status(bankAccountDto.getStatus())
      .build();
  }
}

