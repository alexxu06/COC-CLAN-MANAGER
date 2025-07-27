package com.example.cocapi.proxies;

import com.example.cocapi.models.Clan;
import com.example.cocapi.models.Player;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.http.HttpHeaders;

@Component
public class ClanProxy extends Proxy {

    public ClanProxy(WebClient webClient) {
        super(webClient);
    }

    public Mono<Clan> createClanObject(String tag) {
        URI uri = prepUri(clanAPI, "clans/{tag}", tag);

        return webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(Clan.class);
    }

}
