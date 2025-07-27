package com.example.cocapi.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateWars {

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateWars() {

    }

}
