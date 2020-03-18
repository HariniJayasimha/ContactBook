package com.plivo.contactbook.repository;

import com.plivo.contactbook.model.ContactBook;

import java.util.List;

public interface CustomContactBookRepository {
    List<ContactBook> searchByEmail(String ownerId,String email, int page, int limit);

    List<ContactBook> searchByName(String ownerId, String name, int page, int limit);

    List<ContactBook> searchByNameAndEmail(String ownerId, String name, String email, int page, int limit);
}
