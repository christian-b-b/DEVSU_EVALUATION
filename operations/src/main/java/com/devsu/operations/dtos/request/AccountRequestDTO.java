package com.devsu.operations.dtos.request;


import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;


@Data
@Builder
public class AccountRequestDTO {

    private Long idCustomer;
    private String accountNumber;
    private String accountTypeCode;
    private BigDecimal initialBalance;

}
