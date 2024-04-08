package com.devsu.operations.dtos.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MovementRequestDTO {

    private String accountNumber;
    private String movementTypeCode;
    private BigDecimal amount;


}
