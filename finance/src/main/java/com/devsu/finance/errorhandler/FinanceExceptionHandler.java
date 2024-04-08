package com.devsu.finance.errorhandler;

import com.devsu.finance.constants.FinanceConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class FinanceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpStatusCodeException.class)
    protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex) {
        FinanceError financeError = null;
        if (ex.getStatusCode().is4xxClientError()) {
            financeError = FinanceError.builder().httpStatus(ex.getStatusCode())
                    .code(FinanceConstants.PREFIX_CLIENT_ERROR).build();
        } else if (ex.getStatusCode().is5xxServerError()) {
            financeError = FinanceError.builder().httpStatus(ex.getStatusCode())
                    .code(FinanceConstants.PREFIX_SERVER_ERROR).build();
        }
        financeError.setMessage(ex.getMessage());
        log.error("Error HTTP Request Client: {}", ex.getMessage());
        return buildResponseEntity(financeError);
    }

    @ExceptionHandler(FinanceNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(FinanceNotFoundException ex) {
        log.error("Entity Not Found: {}", ex.getMessage());
        if (ex.getCode()!=null){
            return buildResponseEntity(FinanceError.builder().httpStatus(HttpStatus.NOT_FOUND)
                    .code(ex.getCode())
                    .message(ex.getMessage()).build());

        }else{
            return buildResponseEntity(FinanceError.builder().httpStatus(HttpStatus.NOT_FOUND)
                    .code(FinanceConstants.PREFIX_CLIENT_ERROR + FinanceConstants.NOT_FOUND)
                    .message(ex.getMessage()).build());
        }

    }

    @ExceptionHandler(FinanceConflictException.class)
    protected ResponseEntity<Object> handleConflict(FinanceConflictException ex) {
        log.error("Conflict: {}", ex.getMessage());
        return buildResponseEntity(FinanceError.builder().httpStatus(HttpStatus.CONFLICT)
                .code(FinanceConstants.PREFIX_CLIENT_ERROR + FinanceConstants.CONFLICT)
                .message(ex.getMessage()).build());
    }

    @ExceptionHandler(FinanceUnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorized(FinanceUnauthorizedException ex) {
        log.error("Unauthorized: {}", ex.getMessage());
        if (ex.getCode() == null){
            FinanceError financeError = FinanceError.builder().httpStatus(HttpStatus.UNAUTHORIZED)
                    .code(FinanceConstants.PREFIX_CLIENT_ERROR + FinanceConstants.UNAUTHORIZED)
                    .message(ex.getMessage()).build();
            return buildResponseEntity(financeError);
        }else{
            FinanceError financeError = FinanceError.builder().httpStatus(ex.getHttpStatus())
                    .code(ex.getCode())
                    .message(ex.getMessage()).build();
            return buildResponseEntity(financeError);
        }
    }

    @ExceptionHandler(FinanceGenericServerException.class)
    protected ResponseEntity<Object> handleGenericServerError(FinanceGenericServerException ex) {
        FinanceError financeError = null;
        if (ex.getCode() != null) {
            financeError = FinanceError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .code(ex.getCode()).build();
        } else {
            financeError = FinanceError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .code(FinanceConstants.PREFIX_SERVER_ERROR + FinanceConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
        financeError.setMessage(ex.getMessage());
        log.error("Internal Server Error: {}", ex.getMessage());
        return buildResponseEntity(financeError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Bad Request: {}", ex.getBindingResult().getFieldError().toString());
        return buildResponseEntity(FinanceError.builder().httpStatus(HttpStatus.BAD_REQUEST)
                .code(FinanceConstants.PREFIX_CLIENT_ERROR + FinanceConstants.BAD_REQUEST)
                .message(ex.getBindingResult().getFieldError().toString())
                .subErrors(fillValidationErrorsFrom(ex)).build());
    }

    @ExceptionHandler(FinanceGenericClientException.class)
    protected ResponseEntity<Object> handleGenericClientException(FinanceGenericClientException ex) {
        log.error("Client Error: {}", ex.getMessage());
        return buildResponseEntity(FinanceError.builder().httpStatus(ex.getHttpStatus())
                .code(FinanceConstants.PREFIX_CLIENT_ERROR).message(ex.getMessage())
                .subErrors(ex.getSubErrors()).build());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleError(Exception ex) {
        log.error("Server Error: {}", ex.getMessage());
        return buildResponseEntity(FinanceError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(FinanceConstants.PREFIX_SERVER_ERROR + FinanceConstants.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage()).build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return buildResponseEntity(FinanceError.builder().httpStatus(HttpStatus.BAD_REQUEST)
                .code(FinanceConstants.PREFIX_CLIENT_ERROR + FinanceConstants.BAD_REQUEST)
                .message(ex.getMessage()).build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        String message = ex.getParameterName() + " parameter is missing";
        log.error("Bad Request: {}", message);
        return buildResponseEntity(FinanceError.builder().httpStatus(HttpStatus.BAD_REQUEST)
                .code(FinanceConstants.PREFIX_CLIENT_ERROR + FinanceConstants.BAD_REQUEST)
                .message(message).build());
    }

    private ResponseEntity<Object> buildResponseEntity(FinanceError financeError) {
        return new ResponseEntity<>(financeError, financeError.getHttpStatus());
    }

    protected List<FinanceSubError> fillValidationErrorsFrom(
            MethodArgumentNotValidException argumentNotValid) {
        List<FinanceSubError> subErrorCollection = new ArrayList<>();
        argumentNotValid.getBindingResult().getFieldErrors().get(0).getRejectedValue();
        argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
            subErrorCollection.add(FinanceValidationError.builder().object(objError.getObjectName())
                    .field(objError.getField()).rejectValue(objError.getRejectedValue())
                    .message(objError.getDefaultMessage()).build());
        });
        return subErrorCollection;
    }
}
