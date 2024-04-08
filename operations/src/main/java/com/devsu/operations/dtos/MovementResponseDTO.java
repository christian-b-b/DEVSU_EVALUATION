package com.devsu.operations.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MovementResponseDTO {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean state;
    private String movement;

}
