package com.shishir.gateways.util;

import com.shishir.gateways.exceptions.ValidationException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class ValidationCheckUtil {
    public static void checkBindingResultAndThrow(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                errors.add(field + " : " + message);
            });
            throw new ValidationException(errors);
        }
    }
}
