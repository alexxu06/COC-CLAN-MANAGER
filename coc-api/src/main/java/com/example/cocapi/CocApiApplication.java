package com.example.cocapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CocApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocApiApplication.class, args);
    }

}
