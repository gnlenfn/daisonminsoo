package com.potential.hackathon.controller;

import com.potential.hackathon.dto.request.LoginDto;
import com.potential.hackathon.dto.request.UserDto;
import com.potential.hackathon.dto.request.UserPatchDto;
import com.potential.hackathon.dto.response.UserResponseDto;
import com.potential.hackathon.entity.Users;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.UserRepository;
import com.potential.hackathon.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    @Operation(summary = "email, password로 회원정보 조회")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid LoginDto info) {
        Users user = userRepository.findByEmail(info.getEmail())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        UserResponseDto response = userService.checkUserInfo(info, user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @Operation(summary = "유저 생성, 회원 가입")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserDto userDto) {
        UserResponseDto user = userService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "회원 조회")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID userId) {
        UserResponseDto user = UserResponseDto.findFromUsers(userService.findUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "회원 삭제")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable UUID userId) {
        UserResponseDto result = userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping
    @Operation(summary = "유저 정보 수정")
    public ResponseEntity<UserResponseDto> editUser(@RequestBody @Valid UserPatchDto userDto) {
        UserResponseDto response = userService.updateUser(userDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
