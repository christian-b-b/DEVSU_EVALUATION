package com.devsu.operations.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationConflictException extends RuntimeException{
    private String message;
}
