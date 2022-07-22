package com.tech.usermanagement;

import com.tech.usermanagement.controller.UserController;
import com.tech.usermanagement.repository.UsersRepository;
import com.tech.usermanagement.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author xdebugger
 */
@SpringBootTest
@ActiveProfiles("test")
class UserManagementApplicationH2Tests {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserController userController;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void contextLoads() {
        assertThat(usersService).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(usersRepository).isNotNull();
    }
}
