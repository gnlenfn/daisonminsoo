package com.potential.hackathon.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final RandomNumberGenerator generator;

    @PostMapping("/mails")
    public ResponseEntity sendVerificationMail(@RequestBody EmailDto body) throws NoSuchAlgorithmException {
        emailService.sendEmail(body.getEmail(), "인증메일", generator.createCode());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
