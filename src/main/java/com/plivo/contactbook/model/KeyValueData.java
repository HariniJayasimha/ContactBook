package com.plivo.contactbook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class KeyValueData {

    @NotEmpty
    private String key;

    @NotEmpty
    private String value;
}
