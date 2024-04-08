package com.devsu.finance.models;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Entity
@Table(name = "T_PERSON",schema = "FINANCE_SCHEMA")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPerson")
    private Long idPerson;
    @Column(name = "names")
    private String names;
    @Column(name = "firstLastName")
    private String firstLastName;
    @Column(name = "secondLastName")
    private String secondLastName;
    @Column(name = "genderCode")
    private String genderCode;
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @Column(name = "documentTypeCode")
    private String documentTypeCode;
    @Column(name = "documentNumber")
    private String documentNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "cellphone")
    private String cellphone;
}
