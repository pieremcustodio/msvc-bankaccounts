package com.piere.bootcamp.bankaccounts.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.piere.bootcamp.bankaccounts.model.document.Movement;
import com.piere.bootcamp.bankaccounts.model.enums.MovementTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MovementDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovementDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate createAt;

  private Double amount;

  private MovementTypeEnum movementType;

  private String description;
  
  public static MovementDto build() {
    return MovementDto.builder().build();
  }

  public MovementDto toDto(Movement movement) {
    return MovementDto.builder()
      .id(movement.getId())
      .createAt(movement.getCreateAt())
      .amount(movement.getAmount())
      .movementType(movement.getMovementType())
      .description(movement.getDescription())
      .build();
  }

  public Movement toEntity(MovementDto movementDto) {
    return Movement.builder()
      .id(movementDto.getId())
      .createAt(movementDto.getCreateAt())
      .amount(movementDto.getAmount())
      .movementType(movementDto.getMovementType())
      .description(movementDto.getDescription())
      .build();
  }
}

