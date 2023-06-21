package com.example.scbiblioteca.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException{

    private HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException() {
        super("Invalid id.");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
