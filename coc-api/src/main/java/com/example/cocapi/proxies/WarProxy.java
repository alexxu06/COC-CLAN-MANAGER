package com.example.cocapi.proxies;

import com.example.cocapi.models.war.War;
import com.example.cocapi.models.war.WarResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;

@Component
public class WarProxy extends Proxy {

    public WarProxy(WebClient webClient) {
        super(webClient);
    }

    public Flux<War> getWars(String tag) {
        URI uri = prepUri(warAPI, "{tag}/warhits?timestamp_start=0&timestamp_end=2527625513", tag);

        return webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(WarResponse.class)
                .flatMapMany(warResponse -> Flux.fromIterable(warResponse.getItems()));
    }
}
