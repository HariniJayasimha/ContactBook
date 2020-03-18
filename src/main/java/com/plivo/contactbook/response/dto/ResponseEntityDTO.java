package com.plivo.contactbook.response.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseEntityDTO<T> {

    private T data;

    private Boolean success;

    private String message;

    private Long timestamp;

    public ResponseEntityDTO(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
        this.timestamp = (new Date()).getTime();
    }
}
