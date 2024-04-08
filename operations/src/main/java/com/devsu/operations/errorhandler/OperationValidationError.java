package com.devsu.operations.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OperationValidationError extends OperationSubError{
    private String object;
    private String field;
    private Object rejectValue;
    private String message;
}
