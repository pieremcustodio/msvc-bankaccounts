package com.piere.bootcamp.bankaccounts.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
   * Type of movement
   */
public enum MovementTypeEnum {
    DEPOSITO("DEPOSITO"),
    
    RETIRO("RETIRO");

    private String value;

    MovementTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MovementTypeEnum fromValue(String value) {
      for (MovementTypeEnum b : MovementTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
