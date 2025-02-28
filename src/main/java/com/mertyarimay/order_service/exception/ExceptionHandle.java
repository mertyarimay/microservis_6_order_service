package com.mertyarimay.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemsDetails businessException(BusinessException businessException){
        ProblemsDetails problemsDetails=new ProblemsDetails();
        problemsDetails.setMessage(businessException.getMessage());
        return problemsDetails;
    }
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ValidationException validationProblems(MethodArgumentNotValidException methodArgumentNotValidException){
        ValidationException validationException=new ValidationException();
        validationException.setMessage("Validation Exception");
        validationException.setValidationErorrs(new HashMap<>());
        for (FieldError fieldError:methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            validationException.getValidationErorrs().put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return validationException;
    }





    }
