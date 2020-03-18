package com.plivo.contactbook.service;

import com.plivo.contactbook.model.ContactBook;
import com.plivo.contactbook.request.dto.ContactDTO;
import com.plivo.contactbook.response.dto.ResponseEntityDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactBookService {
    ResponseEntityDTO<String> createContact(ContactDTO requestDTO);

    ResponseEntityDTO<Page<ContactBook>> getContactList(int page, int limit);

    ResponseEntityDTO<List<ContactBook>> searchContacts(String name, String email, int page, int limit);
}
