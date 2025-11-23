package com.controle_estoque_back.exceptions;

public class StatusEntityNotFoundException extends RuntimeException {
    public StatusEntityNotFoundException(String msg) {
        super(msg);
    }
}
