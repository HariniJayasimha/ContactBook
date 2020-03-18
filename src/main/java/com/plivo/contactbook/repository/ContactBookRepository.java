package com.plivo.contactbook.repository;

import com.plivo.contactbook.model.ContactBook;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactBookRepository extends MongoRepository<ContactBook, ObjectId>, PagingAndSortingRepository<ContactBook, ObjectId> {

    ContactBook findByPrimaryEmail(String primaryEmail);

    Page<ContactBook> findAll(Pageable pageable);

    Page<ContactBook> findByOwnerId(String ownerId, Pageable pageable);
}
