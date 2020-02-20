package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by limi on 2017/10/13.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExc extends RuntimeException {

    public NotFoundExc() {
    }

    public NotFoundExc(String message) {
        super(message);
    }

    public NotFoundExc(String message, Throwable cause) {
        super(message, cause);
    }
}
