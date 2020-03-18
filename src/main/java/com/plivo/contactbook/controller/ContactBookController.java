package com.plivo.contactbook.controller;

import com.plivo.contactbook.model.ContactBook;
import com.plivo.contactbook.request.dto.ContactDTO;
import com.plivo.contactbook.response.dto.ResponseEntityDTO;
import com.plivo.contactbook.service.ContactBookService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/contacts")
public class ContactBookController {

    @Inject
    ContactBookService contactBookService;

    private final Boolean SUCCESS = true;

    @GetMapping( value = "/check",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntityDTO<String> checkResponse(){
        return new ResponseEntityDTO<>("Contact Book Application is running","", SUCCESS);
    }

    @PostMapping(path = "/create", produces="application/json")
    public ResponseEntityDTO<String> createContact(@Valid @RequestBody ContactDTO requestDTO){
        return contactBookService.createContact(requestDTO);
    }

    @GetMapping( value = "/list", produces = APPLICATION_JSON_VALUE)
    public ResponseEntityDTO<Page<ContactBook>> getContactList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "limit", defaultValue = "10") int limit){
        return contactBookService.getContactList(page, limit);
    }

    @GetMapping( value = "/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntityDTO<List<ContactBook>> searchContacts(@RequestParam(value = "name", required = false) String name,
                                                               @RequestParam(value = "email", required = false) String email,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "limit", defaultValue = "10") int limit){
        return contactBookService.searchContacts(name, email, page, limit);
    }
}
