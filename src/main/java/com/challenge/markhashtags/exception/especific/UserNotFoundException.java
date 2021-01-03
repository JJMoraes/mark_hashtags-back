package com.challenge.markhashtags.exception.especific;

import com.challenge.markhashtags.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("User not found !");
    }
}
