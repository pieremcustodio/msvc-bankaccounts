package com.piere.bootcamp.bankaccounts.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
   * Account Bank Type
   */
public enum BankAccountTypeEnum {
    AHORRO("AHORRO"),

    CORRIENTE("CORRIENTE"),

    PLAZO_FIJO("PLAZO FIJO");

    private String value;

    BankAccountTypeEnum(String value) {
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
    public static BankAccountTypeEnum fromValue(String value) {
        for (BankAccountTypeEnum b : BankAccountTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
