package com.devsu.finance.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinanceNotFoundException extends RuntimeException{
    private String code;
}
