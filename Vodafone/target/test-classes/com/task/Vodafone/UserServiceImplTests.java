package com.task.Vodafone;

import com.task.Vodafone.dto.UserDto;
import com.task.Vodafone.entity.User;
import com.task.Vodafone.repository.UserRepository;
import com.task.Vodafone.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindAllUsers() {
        // Mocking the userRepository behavior
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Doe");
        user2.setEmail("jane@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Call the service method
        List<UserDto> userDtos = userService.findAllUsers();

        // Verify the result
        assertThat(userDtos).isNotNull();
        assertThat(userDtos).hasSize(2);

        // You can perform more specific assertions based on your conversion logic
        assertThat(userDtos.get(0)).extracting("firstName", "lastName")
                .containsExactly("John", "Doe");
        assertThat(userDtos.get(1)).extracting("firstName", "lastName")
                .containsExactly("Jane", "Doe");
    }
}