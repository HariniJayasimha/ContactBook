package com.plivo.contactbook.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "contactBooks")
@CompoundIndexes({
        @CompoundIndex(name = "owner_id", def = "{'ownerId': 1}"),
        @CompoundIndex(name = "owner_firstName", def = "{'ownerId': 1, 'firstName': 1}"),
        @CompoundIndex(name = "owner_middleName", def = "{'ownerId': 1, 'middleName': 1}"),
        @CompoundIndex(name = "owner_lastName", def = "{'ownerId': 1, 'lastName': 1}"),
        @CompoundIndex(name = "owner_primaryEmail", def = "{'ownerId': 1, 'primaryEmail': 1}"),
        @CompoundIndex(name = "owner_secondaryEmails", def = "{'ownerId': 1, 'secondaryEmails.value': 1}")
})
@Data
@TypeAlias("contactBooks")
public class ContactBook {

    @Id
    @Field(value = "_id")
    private ObjectId contactId;

    private String ownerId;

    private String firstName;

    private String middleName;

    private String lastName;

    @Indexed(unique = true)
    private String primaryEmail;

    private List<KeyValueData> secondaryEmails;

    private List<KeyValueData> contactNumbers;

    private Long createdOn;

    private Long lastUpdatedOn;
}
