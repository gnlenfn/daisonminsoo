package com.potential.hackathon.email;

import com.potential.hackathon.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {

    private final EmailService emailService;
    private final RedisService redisService;

    private static final String AUTH_CODE_PREFIX = "AUTH_CODE ";

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

    public void sendAndSaveCode(String toEmail) throws NoSuchAlgorithmException {
        String subject = "이고모야 인증번호";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, subject, authCode);
        redisService.setNumber(AUTH_CODE_PREFIX + toEmail, authCode);
    }

    public boolean verifyCode(String email, String authCode) {
        String redisCode = redisService.getNumber(AUTH_CODE_PREFIX + email);
        return authCode.equals(redisCode);
    }

}
