package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Value("${test.variable}")
    String value;
    /**
     *
     * @return status of the microservices
     */
    @GetMapping("")
    public String getHealth(){
        return "Status Alive" + value;
    }
}
