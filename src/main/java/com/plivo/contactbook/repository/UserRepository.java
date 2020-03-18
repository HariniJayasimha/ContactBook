package com.plivo.contactbook.repository;

import com.plivo.contactbook.model.ContactBookUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<ContactBookUser, ObjectId> {

    ContactBookUser findByUserName(String userName);
}
