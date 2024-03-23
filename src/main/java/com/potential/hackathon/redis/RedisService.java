package com.potential.hackathon.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RedisService {

    private StringRedisTemplate redisTemplate;

    public String getNumber(String email) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(email)).trim();
    }

    public void setNumber(String email, String number) {
        redisTemplate.opsForValue().set(email, number, Duration.ofMinutes(3));
    }

    public String deleteKey(String email) {
        redisTemplate.delete(email);
        return email;
    }
}
