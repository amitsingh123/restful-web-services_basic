package com.amit.rest.restfulwebservices.exception;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}

