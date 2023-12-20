package com.example.springbootecommerce.exceptionHandler;

public class CategoryException extends Exception{
    private static final long serialVersionUID = 1L;
    private  String errorMsg;

    public CategoryException() {
    }
    public CategoryException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
