package com.example.demo;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apipipiepi")
public class TestRest {
    @GetMapping("/")
    public String get(@RequestParam(name = "name", defaultValue = "world") String name) {
        return String.format("Hello, %s!", name);
    }
    
    @GetMapping("/test")
    public String getTest() {
        return new Date().toString();
    }
}
