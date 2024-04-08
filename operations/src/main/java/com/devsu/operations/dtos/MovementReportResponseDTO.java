package com.devsu.operations.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MovementReportResponseDTO {

    private LocalDate movementDate;
    private String customer;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean state;
    private String movement;
    private BigDecimal availableBalance;

}
