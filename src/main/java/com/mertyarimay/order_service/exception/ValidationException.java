package com.mertyarimay.order_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationException extends ProblemsDetails{
    private Map<String,String>validationErorrs;
}
