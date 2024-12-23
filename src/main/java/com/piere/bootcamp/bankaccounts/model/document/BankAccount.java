package com.piere.bootcamp.bankaccounts.model.document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.piere.bootcamp.bankaccounts.model.enums.BankAccountTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection="bank_accounts")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(name = "bank_account_type")
    private BankAccountTypeEnum bankAccountType;

    private Double balance;

    @Field(name = "client_id")
    private String clientId;

    @Field(name = "limit_movement")
    private Integer limitMovement;

    private Double maintenance;

    @Field(name = "movement_ids")
    @Builder.Default
    @ToString.Exclude
    private List<String> movementIds = new ArrayList();

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Field(name = "create_at")
    private LocalDate createAt;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Field(name = "end_at")
    private LocalDate endAt;

    private Boolean status;
}
