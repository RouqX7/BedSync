package com.example.BedSync.controllerTests;

import com.example.BedSync.BedSyncApplication;
import com.example.BedSync.models.Role;
import com.example.BedSync.controllers.AuthController;  // Update with the correct package for AuthController
import com.example.BedSync.models.User;
import com.example.BedSync.repos.UserRepository;
import com.example.BedSync.services.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.matchers.JsonPathMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthService userAuthService;

    @Autowired
    private AuthController authController;


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void printApplicationContext() {
        // Uncomment the following line for debugging purposes
        // printBeanNames();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


//    @Test
//    public void testRegisterAdmin() throws Exception {
//        User mockAdmin = new User("1", "John", "Doe", "admin@example.com", "password", new Date(), new Date(), Role.ADMIN, new HashSet<>(), "1234567890", "Security Question", "Security Answer", "admin@example.com", new String[]{"READ", "WRITE"}, "profile-picture.jpg");
//
//        // Update the mock to return a non-null value
//        when(userAuthService.registerAdmin(any(User.class))).thenReturn(mockAdmin);
//
//        // Perform the request and get the result
//        MvcResult result = mockMvc.perform(post("http://localhost:8081/api/auth/register-admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(mockAdmin))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andReturn();
//
//        // Get the response headers
//        MockHttpServletResponse response = result.getResponse();
//        HttpHeaders headers = new HttpHeaders();
//        response.getHeaderNames().forEach(name -> headers.addAll(name, response.getHeaders(name)));
//        System.out.println("Response Headers: " + headers);
//
//        // Debugging information for Location header
//        String location = headers.getFirst(HttpHeaders.LOCATION);
//        System.out.println("Location Header: " + location);
//
//        // Extract the ID from the "Location" header
//        assert location != null;
//        String[] parts = location.split("/");
//        long adminId = Long.parseLong(parts[parts.length - 1]);
//        // Print debugging information
//        System.out.println("Extracted Admin ID: " + adminId);
//
//
//        // Ensure the Location header contains the expected URL
//        assertTrue(location.endsWith("/api/auth/register-admin/" + adminId), "Unexpected Location header: " + location);
//
//        // Fetch the user from the repository using the extracted ID
//        // Fetch the user from the repository using the extracted ID
//        User registeredAdmin = userRepository.findById(String.valueOf(adminId)).orElse(null);
//
//// Print some debugging information
//        System.out.println("Registered Admin ID: " + adminId);
//        System.out.println("Fetched Admin from Repository: " + registeredAdmin);
//
//        // Additional debug information to inspect the state of the database
//        List<User> allUsers = userRepository.findAll();
//        System.out.println("All Users in the Database: " + allUsers);
//
//// Ensure the registeredAdmin is not null
//        assert registeredAdmin != null : "Registered admin is null";
//
//    }





    // Other test cases...

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Uncomment the following method for debugging purposes
    private void printBeanNames() {
        Arrays.stream(webApplicationContext.getBeanDefinitionNames())
                .map(name -> webApplicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }
}
