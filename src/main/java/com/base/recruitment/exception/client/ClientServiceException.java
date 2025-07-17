package com.base.recruitment.exception.client;

public class ClientServiceException extends RuntimeException {
    private long codigo;
    public ClientServiceException(String message, long codigo) {
        super(message);
        this.codigo = codigo;
    }

    public long getCodigo() {
        return this.codigo;
    }
}
