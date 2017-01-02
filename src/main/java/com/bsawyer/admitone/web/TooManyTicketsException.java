package com.bsawyer.admitone.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyTicketsException extends IllegalArgumentException {

    public TooManyTicketsException(String message) {
        super(message);
    }

}
