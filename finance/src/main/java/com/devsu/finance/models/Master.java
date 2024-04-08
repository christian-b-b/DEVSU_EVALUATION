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
@Table(name = "T_MASTER",schema = "FINANCE_SCHEMA")
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMaster")
    private Long idMaster;
    @Column(name = "parentCode")
    private String parentCode;
    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;
    @Column(name = "state")
    private Integer state;
    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
