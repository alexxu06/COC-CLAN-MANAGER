package com.example.cocapi.proxies;

import com.example.cocapi.models.Player;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class PlayerProxy extends Proxy {

    public PlayerProxy(WebClient webClient) {
        super(webClient);
    }

    public Mono<Player> getPlayer(String tag) {
        URI uri = prepUri(clanAPI, "players/{tag}", tag);

        return webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(Player.class);
    }
}
