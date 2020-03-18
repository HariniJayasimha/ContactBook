package com.plivo.contactbook.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "contactBookUsers")
@Data
@TypeAlias("contactBookUsers")
public class ContactBookUser {

    @Id
    @Field(value = "_id")
    private ObjectId userId;

    @Indexed(unique = true)
    private String userName;

    private String password;

    private Long createdOn;

    public ContactBookUser(){}
}
