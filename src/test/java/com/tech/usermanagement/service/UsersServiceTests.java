package com.tech.usermanagement.service;

import com.tech.usermanagement.constants.CommonConstants;
import com.tech.usermanagement.entities.UserEntity;
import com.tech.usermanagement.model.FileUploadSuccessResponse;
import com.tech.usermanagement.model.UserInfo;
import com.tech.usermanagement.repository.UsersRepository;
import com.tech.usermanagement.util.CommonRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * @author xdebugger
 */
@ExtendWith(MockitoExtension.class)
class UsersServiceTests {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    @Test
    void getAllUsers_SuccessTest() {
        List<UserEntity> usersList = new ArrayList<>();
        usersList.add(UserEntity.builder().name("Shekhar").salary(6500.00).build());

        Mockito.when(usersRepository.findBySalaryBetween(anyDouble(), anyDouble(), any(PageRequest.class)))
                .thenReturn(usersList);

        List<UserInfo> usersInfo = usersService.getAllUsers(0d, 8000d, 0, null, null);

        assertThat(usersInfo).isNotNull();
    }

    @Test
    void getAllUsers_FailureTest() {
        List<UserEntity> usersList = new ArrayList<>();
        usersList.add(UserEntity.builder().name("Shekhar").salary(6500.00).build());

        Mockito.lenient().when(usersRepository.findBySalaryBetween(anyDouble(), anyDouble(), any(PageRequest.class)))
                .thenReturn(usersList);

        Exception ex = assertThrows(CommonRuntimeException.class, () -> {
            usersService.getAllUsers(0d, 8000d, 0, 0, null);
        });

        String expectedMessage = "Page size must not be less than one";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void processUploadFile_SuccessTest() throws IOException {
       Resource resource = new ClassPathResource("user_test.csv");
       assertNotNull(resource);

       List<UserEntity> usersList = new ArrayList<>();
       usersList.add(UserEntity.builder().name("Shekhar").salary(3500.05).build());


       MockMultipartFile file = new MockMultipartFile(resource.getFilename(), resource.getInputStream());

       Mockito.when(usersRepository.saveAll(anyList())).thenReturn(usersList);
       FileUploadSuccessResponse response = usersService.processUploadFile(file);

       assertNotNull(response);
       assertEquals(1, response.getSuccess());
    }

    @Test
    void processUploadFile_FailureTest() throws IOException {
        Resource resource = new ClassPathResource("user_failure_test.csv");
        assertNotNull(resource);

        MockMultipartFile file = new MockMultipartFile(resource.getFilename(), resource.getInputStream());
        Exception exception = assertThrows(CommonRuntimeException.class, () -> {
            usersService.processUploadFile(file);
        });

        String expectedMessage = CommonConstants.EXCEPTION_CSVPROCESSING;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
