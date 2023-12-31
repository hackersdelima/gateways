package com.shishir.gateways.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {
    List<String> errors;
    public ValidationException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
