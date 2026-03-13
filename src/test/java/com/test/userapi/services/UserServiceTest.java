package com.test.userapi.services;

import com.test.userapi.Model.User;
import com.test.userapi.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void shouldCreateUserSuccessfully() {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setName("Test User");
        user.setPhone("+52 5555555555");
        user.setPassword("123456");
        user.setTaxId("AAAA990101XXX");

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("test@mail.com", createdUser.getEmail());
    }

    @Test
    void shouldLoginSuccessfully() {

        User user = new User();
        user.setEmail("login@mail.com");
        user.setName("Login User");
        user.setPhone("+52 5555555555");
        user.setPassword("123456");
        user.setTaxId("BBBB990101XXX");

        userService.createUser(user);

        User loggedUser = userService.login("BBBB990101XXX", "123456");

        assertNotNull(loggedUser);
        assertEquals("login@mail.com", loggedUser.getEmail());
    }

    @Test
    void shouldDeleteUserSuccessfully() {

        User user = new User();
        user.setEmail("delete@mail.com");
        user.setName("Delete User");
        user.setPhone("+52 5555555555");
        user.setPassword("123456");
        user.setTaxId("CCCC990101XXX");

        User createdUser = userService.createUser(user);

        UUID userId = createdUser.getId();

        userService.deleteUser(userId);

    }
}