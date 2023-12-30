package com.task.Vodafone.security;

import com.task.Vodafone.entity.User;
import com.task.Vodafone.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // You can add any authorities or roles if needed
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.emptyList()//fixes an issue with framework
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}
