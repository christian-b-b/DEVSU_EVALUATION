package com.devsu.operations.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationNotFoundException extends RuntimeException{
    private String code;
}
