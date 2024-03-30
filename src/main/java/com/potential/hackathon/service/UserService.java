package com.potential.hackathon.service;

import com.potential.hackathon.dto.UserDto;
import com.potential.hackathon.dto.UserPatchDto;
import com.potential.hackathon.dto.UserResponseDto;
import com.potential.hackathon.entity.Users;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users findUserId(UUID userId) {
        return userRepository.findByUniqueUserId(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    public Users findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    @Transactional
    public UserResponseDto createUser(UserDto userDto) {
        Users user = new Users();

        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setNickname(userDto.getNickname());

        userRepository.save(user);

        return UserResponseDto.findFromUsers(user);
    }

    public Long updateUser(UserPatchDto userPatchDto, UUID userId) {
        Users user = findUserId(userId);
        user.setNickname(userPatchDto.getNickname());
        user.setEmail(userPatchDto.getEmail());
        user.setPassword(userPatchDto.getPassword());

        return userRepository.save(user).getId();
    }

    public UserResponseDto findByUniqueUserId(UUID userId) {
        Users user = findUserId(userId);
        return UserResponseDto.findFromUsers(user);
    }

    public void deleteUser(UUID userId) {
        Users user = findUserId(userId);
        userRepository.deleteById(user.getId());
    }
}
