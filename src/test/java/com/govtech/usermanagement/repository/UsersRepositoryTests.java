package com.govtech.usermanagement.repository;

import com.govtech.usermanagement.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author xdebugger
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UsersRepositoryTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void injectedComponentsAreNotNull() {
        Assertions.assertThat(usersRepository).isNotNull();
    }

    @BeforeEach
    void initSetup() {
        usersRepository.save(UserEntity.builder().name("Shekhar").salary(6500.00).build());
    }

    @Test
    void whenSaved_findByName() {
        Assertions.assertThat(usersRepository.findById("Shekhar")).isNotNull();
    }

    @Test
    void whenSaved_findBySalary() {
        Assertions.assertThat(usersRepository.findBySalaryBetween(0d, 8000d, PageRequest.of(0, 1)))
                .isNotNull();
    }
}
