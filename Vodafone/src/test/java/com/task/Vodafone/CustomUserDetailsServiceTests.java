package com.task.Vodafone;

import com.task.Vodafone.entity.User;
import com.task.Vodafone.repository.UserRepository;
import com.task.Vodafone.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByEmailSuccess() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("john@example.com");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("john@example.com");
        assertThat(userDetails.getPassword()).isEqualTo("password123");

    }

    @Test
    public void testLoadUserByEmailNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername("unknown@example.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Invalid username or password.");
    }


}
