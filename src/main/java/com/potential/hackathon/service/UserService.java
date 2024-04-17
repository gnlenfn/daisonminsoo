package com.potential.hackathon.service;

import com.potential.hackathon.dto.request.LoginDto;
import com.potential.hackathon.dto.request.UserDto;
import com.potential.hackathon.dto.request.UserPatchDto;
import com.potential.hackathon.dto.response.UserResponseDto;
import com.potential.hackathon.entity.Users;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.exceptions.UserExistsException;
import com.potential.hackathon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users findUserId(UUID userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    public UserResponseDto checkUserInfo(LoginDto loginDto, Users user) {
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return UserResponseDto.findFromUsers(user);
        }
        throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_MATCH);
    }

    @Transactional
    public UserResponseDto createUser(UserDto userDto) {

        Optional<Users> byEmail = userRepository.findByEmail(userDto.getEmail());
        Optional<Users> byNickname = userRepository.findByNickname(userDto.getNickname());
        if (byEmail.isPresent() | byNickname.isPresent()) {
            throw new UserExistsException(ExceptionCode.USER_EXIST);
        }

        Users user = Users.builder()
                .email(userDto.getEmail())
                .userId(UUID.randomUUID())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .description(userDto.getDescription())
                .build();

        Users save = userRepository.save(user);

        return UserResponseDto.findFromUsers(save);
        }

    public UserResponseDto updateUser(UserPatchDto userPatchDto) {
        Users user = findUserId(userPatchDto.getUserId());
        user.setNickname(userPatchDto.getNickname());
        user.setEmail(userPatchDto.getEmail());
        user.setDescription(userPatchDto.getDescription());
        if (userPatchDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userPatchDto.getPassword()));
        }

        Users response = userRepository.save(user);
        return UserResponseDto.findFromUsers(response);
    }

    public UserResponseDto deleteUser(UUID userId) {
        Users user = findUserId(userId);
        userRepository.deleteById(user.getId());

        return UserResponseDto.findFromUsers(user);
    }
}
