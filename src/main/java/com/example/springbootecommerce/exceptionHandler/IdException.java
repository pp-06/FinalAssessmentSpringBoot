package com.example.springbootecommerce.exceptionHandler;

public class IdException extends Exception {
    private String  errorMsg;

    public IdException() {
    }
    public IdException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
