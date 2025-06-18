package com.example.cocapi.proxies;

import com.example.cocapi.models.Clan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClanProxy {
    private final WebClient webClient;

    @Value("${coc-clanwar-api-url}")
    private String url;

    public ClanProxy(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Clan> createClan(String tag) {
        return webClient.get()
                .uri(url + "%23" + tag + "/previous?timestamp_start=0&timestamp_end=9999999999&limit=10")
                .retrieve()
                .bodyToMono(Clan.class);
    }

}
