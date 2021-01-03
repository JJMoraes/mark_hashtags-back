package com.challenge.markhashtags.exception.specific;

import com.challenge.markhashtags.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("User not found !");
    }
}
