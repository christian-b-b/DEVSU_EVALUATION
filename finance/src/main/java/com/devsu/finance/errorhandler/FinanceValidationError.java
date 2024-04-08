package com.devsu.finance.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FinanceValidationError extends FinanceSubError{
    private String object;
    private String field;
    private Object rejectValue;
    private String message;
}
