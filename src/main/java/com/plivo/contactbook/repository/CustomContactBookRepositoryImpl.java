package com.plivo.contactbook.repository;

import com.plivo.contactbook.model.ContactBook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class CustomContactBookRepositoryImpl implements CustomContactBookRepository {

    @Inject
    private MongoOperations mongoOperations;

    @Override
    public  List<ContactBook> searchByEmail(String ownerId, String email, int page, int limit){
        Criteria emailCriteria = new Criteria();
        emailCriteria.andOperator(Criteria.where("ownerId").is(ownerId), new Criteria().orOperator(Criteria.where("primaryEmail").regex(email),
                Criteria.where("secondaryEmails.value").regex(email)));
        Query queryEmail = new Query(emailCriteria);
        queryEmail.with(PageRequest.of(page, limit));

        return mongoOperations.find(queryEmail, ContactBook.class);
    }

    @Override
    public List<ContactBook> searchByName(String ownerId, String name, int page, int limit){
        Criteria nameCriteria = new Criteria();
        nameCriteria.andOperator(Criteria.where("ownerId").is(ownerId), new Criteria().orOperator(Criteria.where("firstName").regex(name),
                Criteria.where("middleName").regex(name),
                Criteria.where("lastName").regex(name)));
        Query queryName = new Query(nameCriteria);
        queryName.with(PageRequest.of(page, limit));

        return mongoOperations.find(queryName, ContactBook.class);
    }

    @Override
    public List<ContactBook> searchByNameAndEmail(String ownerId, String name, String email, int page, int limit){
        Criteria nameAndEmail = new Criteria();
        nameAndEmail.andOperator(Criteria.where("ownerId").is(ownerId), new Criteria().orOperator(Criteria.where("firstName").regex(name),
                Criteria.where("middleName").regex(name),
                Criteria.where("lastName").regex(name),
                Criteria.where("primaryEmail").regex(email),
                Criteria.where("secondaryEmails.value").regex(email)));

        Query query = new Query(nameAndEmail);
        query.with(PageRequest.of(page, limit));

        return mongoOperations.find(query, ContactBook.class);
    }
}
