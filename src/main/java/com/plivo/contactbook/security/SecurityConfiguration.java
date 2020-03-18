package com.plivo.contactbook.security;

import com.plivo.contactbook.exception.UnAuthorizedException;
import com.plivo.contactbook.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try{
            http
                    .csrf().disable()
                    .authorizeRequests().anyRequest().authenticated()
                    .and().httpBasic()
                    .and().sessionManagement().disable();
        }catch(Exception e){
            throw new UnAuthorizedException("User not authorized to access the requested resource");
        }

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService);
    }
}
