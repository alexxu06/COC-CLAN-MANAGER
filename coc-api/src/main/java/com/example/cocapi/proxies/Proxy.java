package com.example.cocapi.proxies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;

@Component
public abstract class Proxy {
    protected final RestClient restClient;

    @Value("${coc-api-url}")
    protected String clanAPI;

    @Value("${coc-clanwar-api-url}")
    protected String warAPI;

    @Value("${bearer-token}")
    protected String bearerToken;

    public Proxy(RestClient restClient) {
        this.restClient = restClient;
    }

    // Adds a # in front of the tag if there isn't one
    public String prepTag(String tag) {
        return tag.startsWith("#") ? tag : "#" + tag;
    }

    public URI prepUri(String url, String path, String tag) {
        tag = prepTag(tag);

        return UriComponentsBuilder.fromUriString(url + path)
                .build(tag);
    }

    // if a war endtime is provided
    public URI prepUri(String url, String path, String tag, Timestamp endTime) {
        tag = prepTag(tag);
        long endTimeSeconds = endTime.getTime() / 1000;

        return UriComponentsBuilder.fromUriString(url + path)
                .build(tag, endTimeSeconds);
    }

}
