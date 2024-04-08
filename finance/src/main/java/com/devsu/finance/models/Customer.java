package com.devsu.finance.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_CUSTOMER",schema = "FINANCE_SCHEMA")
@PrimaryKeyJoinColumn(name = "idCustomer")
public class Customer extends Person{
    @Column(name = "password")
    private String password;
    @Column(name = "state")
    private Integer state;
    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
