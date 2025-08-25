package com.example.cocapi.proxies;

import com.example.cocapi.models.Player;
import com.example.cocapi.services.TagService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Component
public class PlayerProxy extends Proxy {

    public PlayerProxy(RestClient restClient, TagService tagService) {
        super(restClient, tagService);
    }

    public Player getPlayer(String tag) {
        URI uri = prepUri(clanAPI, "players/{tag}", tag);

        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .body(Player.class);
    }
}
