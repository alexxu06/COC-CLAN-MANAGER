package com.example.cocapi.proxies;

import com.example.cocapi.models.Clan;
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

    public Mono<Clan> sasdasoda(String tag) {
        tag = "%23" + tag;
        URI uri = URI.create(clanAPI + tag);

        return webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6Ijk3MTZkMjYyLTEwZTktNGMwNC1iYzYwLWRiZjBlZGVhNzBhZCIsImlhdCI6MTc1MDIwMDMzNSwic3ViIjoiZGV2ZWxvcGVyLzc0NDFiOWMyLThlNGEtNDgzMy1iZWVmLWZhYjRlNDc0NGUyMyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjQ1Ljc5LjIxOC43OSJdLCJ0eXBlIjoiY2xpZW50In1dfQ.piBWGef7b26InDmsQdNBcC93Rxoc9spkocSdRUC-1n1IUAK9Ke3z4hvN7di6YcauiF9q0HmRxCMDjKFzBaZDEA")
                .retrieve()
                .bodyToFlux(Player.class)
                .collectList()
                .map(players -> {
                    Clan clan = new Clan();
                    clan.setMemberList(players);
                    return clan;
                });
    }
}
