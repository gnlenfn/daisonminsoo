package com.potential.hackathon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageDto {

    private String uploadName;
    private boolean isProfile;

}
