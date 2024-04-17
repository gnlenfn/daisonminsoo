package com.potential.hackathon.controller;

import com.google.gson.Gson;
import com.potential.hackathon.dto.request.UserDto;
import com.potential.hackathon.dto.request.UserPatchDto;
import com.potential.hackathon.dto.response.UserResponseDto;
import com.potential.hackathon.entity.Users;
import com.potential.hackathon.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("회원 가입")
    void createUserTest() throws Exception {
        // given
        UserDto request = createRequest();
        UserResponseDto response = createResponse("egomoya");

        doReturn(response).when(userService).createUser(any(UserDto.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("email", response.getEmail()).exists())
                .andExpect(jsonPath("userId", response.getUserId()).exists())
                .andExpect(jsonPath("nickname", response.getNickname()).exists())
                .andExpect(jsonPath("description", response.getDescription()).exists());

    }

    @Test
    @DisplayName("회원 정보 조회")
    void fetchUser() throws Exception {
        // given
        UserResponseDto response = createResponse("egomoya");
        Users user = Users.builder()
                .id(1L)
                .userId(UUID.fromString("6912161b-6b7b-44f8-864c-cbc76b9a9656"))
                .email("test@email.com")
                .nickname("egomoya")
                .description("hello world!")
                .build();

        doReturn(user).when(userService).findUserId(UUID.fromString("6912161b-6b7b-44f8-864c-cbc76b9a9656"));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/6912161b-6b7b-44f8-864c-cbc76b9a9656")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("email", response.getEmail()).exists())
                .andExpect(jsonPath("userId", response.getUserId()).exists())
                .andExpect(jsonPath("nickname", response.getNickname()).exists())
                .andExpect(jsonPath("description", response.getDescription()).exists());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void deleteUser() throws Exception {
        // given
        Users user = Users.builder()
                .id(1L)
                .userId(UUID.fromString("6912161b-6b7b-44f8-864c-cbc76b9a9656"))
                .email("test@email.com")
                .nickname("egomoya")
                .description("hello world!")
                .build();
        UserResponseDto response = createResponse("egomoya");
        doReturn(UserResponseDto.findFromUsers(user)).when(userService).deleteUser(UUID.fromString("6912161b-6b7b-44f8-864c-cbc76b9a9656"));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/6912161b-6b7b-44f8-864c-cbc76b9a9656")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(response))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("email", response.getEmail()).exists())
                .andExpect(jsonPath("userId", response.getUserId()).exists())
                .andExpect(jsonPath("nickname", response.getNickname()).exists());
    }

//    @Test
//    @DisplayName("회원 정보 수정")
//    void editUser() throws Exception {
//        UserPatchDto patch = UserPatchDto.builder()
//                .userId(UUID.fromString("6912161b-6b7b-44f8-864c-cbc76b9a9656"))
//                .nickname("이거모야")
//                .build();
//
//        UserResponseDto response = createResponse("이거모야");
//
//        doReturn(response).when(userService).updateUser(patch);
//
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.patch("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(patch))
//        );
//
//        resultActions.andExpect(jsonPath("nickname", response.getNickname()).exists())
//                .andExpect(jsonPath("userId", response.getUserId()).exists())
//                .andExpect(jsonPath("email", response.getEmail()).exists())
//                .andExpect(jsonPath("description", response.getDescription()).exists());
//    }

    private UserDto createRequest() {
        return UserDto.builder()
                .email("test@email.com")
                .password("4885")
                .nickname("egomoya")
                .description("hello world!")
                .build();
    }

    private UserResponseDto createResponse(String nickname) {
        return UserResponseDto.builder()
                .email("test@email.com")
                .userId(UUID.fromString("6912161b-6b7b-44f8-864c-cbc76b9a9656"))
                .nickname(nickname)
                .description("hello world!")
                .build();
    }
}
