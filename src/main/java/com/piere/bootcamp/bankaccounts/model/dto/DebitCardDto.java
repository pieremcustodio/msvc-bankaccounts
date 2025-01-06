package com.piere.bootcamp.bankaccounts.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.piere.bootcamp.bankaccounts.model.document.DebitCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DebitCardDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebitCardDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String id;

    private String clientId;

    private String mainAccountId;

    private List<String> associatedAccountIds;

    @ApiModelProperty(hidden = true)
    @ToString.Exclude
    @Builder.Default
    private List<String> movements = new ArrayList();

    private List<String> movementIds;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate expirationDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createAt;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endAt;

    public static DebitCardDto build() {
        return DebitCardDto.builder().build();
    }

    public DebitCardDto toDto(DebitCard debitCard) {
        return DebitCardDto.builder()
            .id(debitCard.getId())
            .clientId(debitCard.getClientId())
            .mainAccountId(debitCard.getMainAccountId())
            .associatedAccountIds(debitCard.getAssociatedAccountIds())
            .expirationDate(debitCard.getExpirationDate())
            .movementIds(debitCard.getMovementIds())
            .createAt(debitCard.getCreateAt())
            .endAt(debitCard.getEndAt())
            .build();
    }

    public DebitCard toEntity(DebitCardDto debitCardDto) {
        return DebitCard.builder()
            .id(debitCardDto.getId())
            .clientId(debitCardDto.getClientId())
            .mainAccountId(debitCardDto.getMainAccountId())
            .associatedAccountIds(debitCardDto.getAssociatedAccountIds())
            .expirationDate(debitCardDto.getExpirationDate())
            .movementIds(debitCardDto.getMovementIds())
            .createAt(debitCardDto.getCreateAt())
            .endAt(debitCardDto.getEndAt())
            .build();
    }
}
