package com.devsu.finance.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinanceConflictException extends RuntimeException{
    private String message;
}
