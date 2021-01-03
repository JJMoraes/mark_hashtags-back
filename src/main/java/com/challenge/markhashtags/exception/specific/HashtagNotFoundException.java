package com.challenge.markhashtags.exception.specific;

import com.challenge.markhashtags.exception.NotFoundException;

public class HashtagNotFoundException extends NotFoundException {

    public HashtagNotFoundException() {
        super("Hashtag not found !");
    }
}
