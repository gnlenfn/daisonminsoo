package com.potential.hackathon.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserPatchDto {
    @NotNull
    private UUID userId;
    @Email
    private String email;
    private String password;
    private String nickname;
    private String description;
}
