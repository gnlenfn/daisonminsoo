package com.potential.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchDto {
    private String nickname;
    private String email;
    private String password;
}
