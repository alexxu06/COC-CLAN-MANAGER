package com.example.cocapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ProjectConfig {
    @Bean
    public RestClient webClient() {
        return RestClient.create();
    }
}
