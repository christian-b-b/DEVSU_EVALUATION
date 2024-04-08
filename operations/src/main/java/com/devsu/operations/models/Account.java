package com.devsu.operations.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_ACCOUNT",schema = "FINANCE_SCHEMA")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAccount")
    private Long idAccount;
    @Column(name = "idCustomer")
    private Long idCustomer;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "accountTypeCode")
    private String accountTypeCode;
    @Column(name = "initialBalance")
    private BigDecimal initialBalance;
    @Column(name = "state")
    private Integer state;
    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
