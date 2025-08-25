package com.example.cocapi.proxies;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.War;
import com.example.cocapi.models.war.WarResponse;
import com.example.cocapi.services.TagService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Component
public class WarProxy extends Proxy {

    public WarProxy(RestClient restClient, TagService tagService) {
        super(restClient, tagService);
    }

    // get ALL wars
    public List<War> getWars(String tag) {
        URI uri = prepUri(warAPI, "{tag}/warhits?timestamp_start=0&timestamp_end=2527625513", tag);

        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .body(WarResponse.class)
                .getItems();
    }

    // get wars after a given time
    public List<War> getRecentWars(Player player) {
        if (player.getWarEndTime() == null) {
            return List.of();
        }

        URI uri = prepUri(warAPI, "{tag}/warhits?timestamp_start={endTime}&timestamp_end=2527625513",
                player.getTag(), player.getWarEndTime());

        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .body(WarResponse.class)
                .getItems();
    }
}
