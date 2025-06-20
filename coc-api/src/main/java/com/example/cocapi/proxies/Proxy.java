package com.example.cocapi.proxies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public abstract class Proxy {
    protected final WebClient webClient;

    @Value("${coc-api-url}")
    protected String clanAPI;

    @Value("${coc-clanwar-api-url}")
    protected String warAPI;

    @Value("${bearer-token}")
    protected String bearerToken;

    public Proxy(WebClient webClient) {
        this.webClient = webClient;
    }
}
