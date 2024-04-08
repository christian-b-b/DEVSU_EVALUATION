package com.devsu.finance.dtos.request;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CustomerRequestDTO {
    private String names;
    private String firstLastName;
    private String secondLastName;
    private String genderCode;
    private LocalDate birthDate;
    private String documentTypeCode;
    private String documentNumber;
    private String address;
    private String cellphone;
    private String password;
}
