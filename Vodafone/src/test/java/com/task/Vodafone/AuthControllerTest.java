package com.task.Vodafone;
import com.task.Vodafone.controller.AuthController;
import com.task.Vodafone.dto.UserDto;
import com.task.Vodafone.entity.User;
import com.task.Vodafone.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @Test
    public void testShowHomePage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
        // .andExpect(view().name("register"))
        // .andExpect(model().attributeExists("user"))
        ;
    }
    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
               // .andExpect(view().name("register"))
               // .andExpect(model().attributeExists("user"))
           ;
    }

    @Test
    public void testRegistrationSuccess() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userService.findUserByEmail("test@example.com")).thenReturn(null);

        mockMvc.perform(post("/register/save")
                        .flashAttr("user", userDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register?success"));

        verify(userService, times(1)).saveUser(userDto);
    }

    @Test
    public void testRegistrationFailure() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        UserDto userDto = new UserDto();
        userDto.setEmail("existing@example.com");

        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        when(userService.findUserByEmail("existing@example.com")).thenReturn(existingUser);

        mockMvc.perform(post("/register/save")
                        .flashAttr("user", userDto))
                .andExpect(status().isOk())
                .andExpect(view().name("/register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeErrorCount("user", 1));

        verify(userService, never()).saveUser(userDto);
    }

    // Add more test methods for other controller actions as needed

}

