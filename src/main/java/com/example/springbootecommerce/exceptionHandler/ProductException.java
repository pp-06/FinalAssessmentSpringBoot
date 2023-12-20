package com.example.springbootecommerce.exceptionHandler;

public class ProductException extends Exception{
    private static final long serialVersionUID = 1L;
    private  String errorMsg;

    public ProductException() {
    }
    public ProductException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
