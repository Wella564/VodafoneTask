package com.task.Vodafone.security;

import com.task.Vodafone.entity.User;
import com.task.Vodafone.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
//Security Class to handle authentication with SpringSecurity Class
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override  //method from Interface
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                   //return a class provided by Spring Security to represent a user in the security context
                   //This is used for the security authentication process
                   //By returning this class you're providing Spring Security with the necessary user details for authentication.
                   // It includes the username (in this case, the email), password, and authorities (roles) of the user.
                    user.getEmail(),
                    user.getPassword(),
                    Collections.emptyList()//Authorities (roles) must be coded otherwise an error will arise
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}
