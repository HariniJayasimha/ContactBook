package com.plivo.contactbook.service.impl;

import com.plivo.contactbook.exception.BadRequestException;
import com.plivo.contactbook.exception.InternalServerException;
import com.plivo.contactbook.model.ContactBook;
import com.plivo.contactbook.model.ContactBookUser;
import com.plivo.contactbook.repository.ContactBookRepository;
import com.plivo.contactbook.repository.CustomContactBookRepository;
import com.plivo.contactbook.repository.UserRepository;
import com.plivo.contactbook.request.dto.ContactDTO;
import com.plivo.contactbook.response.dto.ResponseEntityDTO;
import com.plivo.contactbook.service.ContactBookService;
import com.plivo.contactbook.utils.SessionPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service
public class ContactBookServiceImpl implements ContactBookService {

    @Inject
    ContactBookRepository contactBookRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CustomContactBookRepository customContactBookRepository;

    @Inject
    SessionPrincipal sessionPrincipal;

    @Inject
    private MongoOperations mongoOperations;

    @Override
    public ResponseEntityDTO<String> createContact(ContactDTO requestDTO){
        // Check if a contact with same primary email exists
        ContactBook contact = contactBookRepository.findByPrimaryEmail(requestDTO.getPrimaryEmail());

        if(contact != null){
            throw new BadRequestException("Contact already exists");
        }
        // if not, create new contact
        String userName = sessionPrincipal.getSessionUser();
        ContactBookUser owner = userRepository.findByUserName(userName);

        ContactBook newContact = new ContactBook();

        // Contact Owner
        newContact.setOwnerId(owner.getUserId().toString());

        // Primary (unique) email of the contact
        newContact.setPrimaryEmail(requestDTO.getPrimaryEmail());

        // FirstName, MiddleName and LastName
        newContact.setFirstName(requestDTO.getFirstName());
        if(requestDTO.getMiddleName() != null && requestDTO.getMiddleName().trim() != ""){
            newContact.setMiddleName(requestDTO.getMiddleName());
        }
        if(requestDTO.getLastName() != null && requestDTO.getLastName().trim() != ""){
            newContact.setLastName(requestDTO.getLastName());
        }

        // Secondary email(s) if present
        if(requestDTO.getSecondaryEmails() != null && !requestDTO.getSecondaryEmails().isEmpty()){
            newContact.setSecondaryEmails(requestDTO.getSecondaryEmails());
        }

        // Contact Number(s) if present
        newContact.setContactNumbers(requestDTO.getContactNumbers());

        newContact.setCreatedOn(new Date().getTime());
        newContact.setLastUpdatedOn((new Date().getTime()));

        ContactBook savedContact = contactBookRepository.save(newContact);

        if(savedContact == null){
            throw new InternalServerException("Contact not saved");
        }

        return new ResponseEntityDTO<>(null, "Contact saved successfully", true);
    }

    @Override
    public ResponseEntityDTO<Page<ContactBook>> getContactList(int page, int limit){

        String userName = sessionPrincipal.getSessionUser();
        ContactBookUser owner = userRepository.findByUserName(userName);

        Page<ContactBook> contacts = contactBookRepository.findByOwnerId(owner.getUserId().toString(), PageRequest.of(page, limit));
        return new ResponseEntityDTO<>(contacts, "", true);
    }

    @Override
    public ResponseEntityDTO<List<ContactBook>> searchContacts(String name, String email, int page, int limit){

        List<ContactBook> contacts = null;
        String userName = sessionPrincipal.getSessionUser();
        ContactBookUser owner = userRepository.findByUserName(userName);

        if(name == null && email != null){
            //Search by Email
            contacts = customContactBookRepository.searchByEmail(owner.getUserId().toString(),email, page, limit);

        }else if(name != null && email == null){
            //Search by Name
            contacts = customContactBookRepository.searchByName(owner.getUserId().toString(),name, page, limit);

        }else if(name != null && email != null){
            //Search by Name and Email
            contacts = customContactBookRepository.searchByNameAndEmail(owner.getUserId().toString(), name, email, page, limit);

        }else{
            throw new BadRequestException("Please specify Name or Email to search");
        }

        return new ResponseEntityDTO<>(contacts, "", true);
    }
}
