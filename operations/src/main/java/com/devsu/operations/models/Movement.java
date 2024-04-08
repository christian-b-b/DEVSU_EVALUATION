package com.devsu.operations.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_MOVEMENT",schema = "FINANCE_SCHEMA")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMovement")
    private Long idMovement;
    @ManyToOne
    @JoinColumn(name = "idAccount")
    private Account account;
    @Column(name = "movementTypeCode")
    private String movementTypeCode;
    @Column(name = "movementDate")
    private LocalDate movementDate;
    @Column(name = "initialBalance")
    private BigDecimal initialBalance;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "state")
    private Integer state;
    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
