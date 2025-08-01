package com.example.cocapi.proxies;

import com.example.cocapi.models.war.War;
import com.example.cocapi.models.war.WarResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

@Component
public class WarProxy extends Proxy {

    public WarProxy(RestClient restClient) {
        super(restClient);
    }

    public List<War> getWars(String tag) {
        URI uri = prepUri(warAPI, "{tag}/warhits?timestamp_start=0&timestamp_end=2527625513", tag);

        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .body(WarResponse.class)
                .getItems();
    }

    public List<War> getSingleWar(String tag) {
        URI uri = prepUri(warAPI, "{tag}/warhits?timestamp_start=0&timestamp_end=2527625513&limit=1", tag);

        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .body(WarResponse.class)
                .getItems();
    }
}
