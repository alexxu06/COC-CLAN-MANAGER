package com.example.cocapi.proxies;

import com.example.cocapi.models.war.WarResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class PlayerWarProxy extends Proxy {

    public PlayerWarProxy(WebClient webClient) {
        super(webClient);
    }

    public Mono<WarResponse> getWars(String tag) {
        tag = "%23" + tag;
        URI uri = URI.create(warAPI + tag + "/warhits?timestamp_start=0&timestamp_end=2527625513&limit=15");

        return webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(WarResponse.class);
    }
}
