package com.base.recruitment.exception.client;

public class ClientServiceException extends RuntimeException {
    private int codigo;
    public ClientServiceException(String message, int codigo) {
        super(message);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }
}
