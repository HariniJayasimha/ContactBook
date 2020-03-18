package com.plivo.contactbook.request.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.plivo.contactbook.model.KeyValueData;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDTO {

   @NotBlank(message = "FirstName cannot be blank")
    private String firstName;

    private String middleName;

    private String lastName;

    @NotBlank(message = "PrimaryEmail cannot be blank")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String primaryEmail;

    private List<KeyValueData> secondaryEmails;

    @NotNull(message =  "Please provide contact number(s)")
    private List<KeyValueData> contactNumbers;

}
