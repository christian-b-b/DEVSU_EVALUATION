package com.devsu.finance.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseDTO {
    private Long idCustomer;
    private String names;
    private String address;
    private String cellphone;
    private String password;
    private boolean state;
}
