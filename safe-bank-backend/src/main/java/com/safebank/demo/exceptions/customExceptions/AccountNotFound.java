package com.safebank.demo.exceptions.customExceptions;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound() {
        super("Conta não encontrada!");
    }
    
    public AccountNotFound(String message) {
        super(message);
    }
    
}