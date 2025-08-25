package com.example.cocapi.proxies;

import com.example.cocapi.models.Clan;
import com.example.cocapi.services.TagService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Component
public class ClanProxy extends Proxy {

    public ClanProxy(RestClient restClient, TagService tagService) {
        super(restClient, tagService);
    }

    public Clan createClanObject(String tag) {
        URI uri = prepUri(clanAPI, "clans/{tag}", tag);

        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .body(Clan.class);
    }

}
