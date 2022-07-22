package com.govtech.usermanagement.controller;

import com.govtech.usermanagement.entities.UserEntity;
import com.govtech.usermanagement.model.FileUploadSuccessResponse;
import com.govtech.usermanagement.model.UserInfo;
import com.govtech.usermanagement.service.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author xdebugger
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @Test
    void getUsersInfo_SuccessTest() throws Exception {
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(UserInfo.builder().name("Shekhar").salary(3500.05).build());

        Mockito.when(usersService.getAllUsers(anyDouble(), anyDouble(), anyInt(), anyInt(), anyString()))
                .thenReturn(userInfos);

        MvcResult mvcResult = mockMvc.perform(post("/usersInfo/users")
                        .param("offset", "0")
                        .param("limit", "1")
                        .param("sort", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Shekhar"));
    }

    @Test
    void getUsersInfo_Failure_SortBy_Test() throws Exception {
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(UserInfo.builder().name("Shekhar").salary(3500.05).build());

        Mockito.when(usersService.getAllUsers(anyDouble(), anyDouble(), anyInt(), anyInt(), anyString()))
                .thenReturn(userInfos);

        MvcResult mvcResult = mockMvc.perform(post("/usersInfo/users")
                        .param("offset", "0")
                        .param("limit", "1")
                        .param("sort", "invalidname")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.BAD_GATEWAY.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void getUsersInfo_Failure_limit_Test() throws Exception {
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(UserInfo.builder().name("Shekhar").salary(3500.05).build());

        Mockito.when(usersService.getAllUsers(anyDouble(), anyDouble(), anyInt(), anyInt(), anyString()))
                .thenReturn(userInfos);

        MvcResult mvcResult = mockMvc.perform(post("/usersInfo/users")
                        .param("offset", "0")
                        .param("limit", "0")
                        .param("sort", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.BAD_GATEWAY.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void getUsersInfo_Failure_MinMax_Test() throws Exception {
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(UserInfo.builder().name("Shekhar").salary(3500.05).build());

        Mockito.when(usersService.getAllUsers(anyDouble(), anyDouble(), anyInt(), anyInt(), anyString()))
                .thenReturn(userInfos);

        MvcResult mvcResult = mockMvc.perform(post("/usersInfo/users")
                        .param("offset", "0")
                        .param("limit", "1")
                        .param("sort", "name")
                        .param("min", "2000")
                        .param("max", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.BAD_GATEWAY.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void uploadUsersInfo_SuccessTest() throws Exception {
        Resource resource = new ClassPathResource("user_test.csv");
        assertNotNull(resource);

        MockMultipartFile file = new MockMultipartFile("file", resource.getFilename(), "text/csv" ,
                resource.getInputStream());

        Mockito.when(usersService.processUploadFile(any(MultipartFile.class))).thenReturn(FileUploadSuccessResponse
                .builder().success(1).build());

        MvcResult mvcResult = mockMvc.perform(multipart("/usersInfo/upload")
                .file(file))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void uploadUsersInfo_Failure_EmptyFile_Test() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test_empty.csv",
                "text/csv", "".getBytes(StandardCharsets.UTF_8));

        MvcResult mvcResult = mockMvc.perform(multipart("/usersInfo/upload")
                        .file(file))
                .andReturn();

        assertEquals(HttpStatus.BAD_GATEWAY.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void uploadUsersInfo_Failure_File_Type_Test() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "Testing".getBytes(StandardCharsets.UTF_8));

        MvcResult mvcResult = mockMvc.perform(multipart("/usersInfo/upload")
                        .file(file))
                .andReturn();

        assertEquals(HttpStatus.BAD_GATEWAY.value(), mvcResult.getResponse().getStatus());
    }

}
