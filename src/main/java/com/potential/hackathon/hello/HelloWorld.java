package com.potential.hackathon.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/")
    public String hello() {
        return "hello world!";
    }

    @GetMapping("/hello/{id}")
    public String name(@PathVariable String id) {
        return "hello " + id;
    }
}
