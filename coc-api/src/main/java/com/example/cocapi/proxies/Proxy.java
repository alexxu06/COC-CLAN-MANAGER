package com.example.cocapi.proxies;

import com.example.cocapi.services.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;

@Component
public abstract class Proxy {
    protected final RestClient restClient;
    private final TagService tagService;

    @Value("${coc-api-url}")
    protected String clanAPI;

    @Value("${coc-clanwar-api-url}")
    protected String warAPI;

    @Value("${bearer-token}")
    protected String bearerToken;

    public Proxy(RestClient restClient, TagService tagService) {
        this.restClient = restClient;
        this.tagService = tagService;
    }

    public URI prepUri(String url, String path, String tag) {
        tag = tagService.prepTag(tag);

        return UriComponentsBuilder.fromUriString(url + path)
                .build(tag);
    }

    public URI prepUri(String url, String path, String tag, int limit) {
        tag = tagService.prepTag(tag);

        return UriComponentsBuilder.fromUriString(url + path)
                .buildAndExpand(tag, limit)
                .toUri();
    }

    // if a war endtime is provided
    public URI prepUri(String url, String path, String tag, Timestamp endTime) {
        tag = tagService.prepTag(tag);
        long endTimeSeconds = endTime.getTime() / 1000;

        return UriComponentsBuilder.fromUriString(url + path)
                .build(tag, endTimeSeconds);
    }

}
