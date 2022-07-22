package com.govtech.usermanagement;

import com.govtech.usermanagement.controller.UserController;
import com.govtech.usermanagement.repository.UsersRepository;
import com.govtech.usermanagement.service.UsersService;
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
