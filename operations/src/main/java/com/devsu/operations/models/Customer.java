package com.devsu.operations.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long idCustomer;
    private String names;
    private String address;
    private String cellphone;
    private String password;
    private boolean state;


}
