package com.potential.hackathon.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final VerificationService verificationService;

    @PostMapping("/mails")
    public ResponseEntity sendVerificationMail(@RequestBody EmailDto body) throws NoSuchAlgorithmException {
        verificationService.sendAndSaveCode(body.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mails/verification")
    public ResponseEntity verification(@RequestParam String email, @RequestParam String authCode) {
        boolean result = verificationService.verifyCode(email, authCode);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
