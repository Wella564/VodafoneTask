package com.task.Vodafone;

import com.task.Vodafone.controller.AuthController;
import com.task.Vodafone.dto.UserDto;
import com.task.Vodafone.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterFormSubmission() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));

        mockMvc.perform(MockMvcRequestBuilders.post("/register/save")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "john.doe@example.com")
                        .param("password", "password123"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register?success"));

        // Optionally, you can verify interactions with your mocked UserService
        // For example, you might want to verify that the saveUser method was called
            Mockito.verify(userService, Mockito.times(1)).saveUser(Mockito.any(UserDto.class));

    }
    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }
    @Test
    @WithMockUser(username = "mo@example.com", password = "password123")
    void testLoginFormSubmissionSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", "mo@example.com")
                        .param("password", "password123"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/loggedin"))
                .andExpect(result -> {
                    String redirectedUrl = result.getResponse().getRedirectedUrl();
                    System.out.println("Redirected URL: " + redirectedUrl);});
    }
    @Test
    @WithMockUser
    void testLoginFormSubmissionFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", "john.doe@example.com")
                        .param("password", "incorrectPassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"))
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.model().attribute("error", "Invalid credentials"));
    }
}
