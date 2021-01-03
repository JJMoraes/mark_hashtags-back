package com.challenge.markhashtags.exception.especific;

import com.challenge.markhashtags.exception.BadRequestException;

public class BadCredentialsException extends BadRequestException {

    public BadCredentialsException() {
        super("Bad Credentials");
    }
}
