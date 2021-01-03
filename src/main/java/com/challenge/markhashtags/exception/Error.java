package com.challenge.markhashtags.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Error {

    private int code;
    private String msg;
    private Date date;
    private List<String> errors;
}

