package com.potential.hackathon.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchDto {
    @NotBlank
    private String nickname;
    @Email
    @NotBlank
    private String email;
    private String password;
}
