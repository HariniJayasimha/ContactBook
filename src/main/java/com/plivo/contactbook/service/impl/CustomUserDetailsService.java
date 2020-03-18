package com.plivo.contactbook.service.impl;

import com.plivo.contactbook.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.plivo.contactbook.model.ContactBookUser;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Inject
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ContactBookUser user = userRepository.findByUserName(userName);

        if(user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}
