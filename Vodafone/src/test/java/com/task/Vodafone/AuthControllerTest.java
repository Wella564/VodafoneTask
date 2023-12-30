import com.task.Vodafone.controller.AuthController;
import com.task.Vodafone.dto.UserDto;
import com.task.Vodafone.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthController.class)
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
        // Mockito.verify(userService, Mockito.times(1)).saveUser(Mockito.any(UserDto.class));
    }
}
