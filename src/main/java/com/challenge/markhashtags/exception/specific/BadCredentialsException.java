package com.challenge.markhashtags.exception.specific;

import com.challenge.markhashtags.exception.BadRequestException;

public class BadCredentialsException extends BadRequestException {

    public BadCredentialsException() {
        super("Bad Credentials");
    }
}
