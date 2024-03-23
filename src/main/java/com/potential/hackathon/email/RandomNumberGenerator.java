package com.potential.hackathon.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
@Service
public class RandomNumberGenerator {

    public String createCode() throws NoSuchAlgorithmException {
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            int randomInt = secureRandom.nextInt(900000) + 100000;
            return String.valueOf(randomInt);
        } catch (NoSuchAlgorithmException e) {
            log.debug("RandomNumberGenerator.createCode() exception");
            throw new NoSuchAlgorithmException();
        }
    }
}
