package com.example.scbiblioteca.exception;

import org.springframework.http.HttpStatus;

public class RegraNegocioException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public RegraNegocioException(String msg) {
        super(msg);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
