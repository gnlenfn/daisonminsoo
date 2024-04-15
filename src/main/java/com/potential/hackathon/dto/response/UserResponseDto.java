package com.potential.hackathon.dto.response;

import com.potential.hackathon.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private UUID userId;
    private String email;
    private String nickname;
    private String description;

    public static UserResponseDto findFromUsers(Users user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getDescription()
        );
    }
}
