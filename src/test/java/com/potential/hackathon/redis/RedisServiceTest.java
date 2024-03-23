package com.potential.hackathon.redis;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class RedisServiceTest {

    @Autowired
    private RedisService service;
    final String email = "test@gmail.com";
    final String number = "112233";

    @Test
    @Order(2)
    void getTest() {
        String hello = service.getNumber(email);

        assertThat(hello).isEqualTo(number);
    }

    @Test
    @Order(1)
    void setTest() {
        service.setNumber(email, number);
        String result = service.getNumber(email);

        assertThat(result).isEqualTo(number);
    }
}