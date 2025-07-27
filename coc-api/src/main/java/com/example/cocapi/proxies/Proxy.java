package com.example.cocapi.proxies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public abstract class Proxy {
    protected final WebClient webClient;

    @Value("${coc-api-url}")
    protected String clanAPI;

    @Value("${coc-clanwar-api-url}")
    protected String warAPI;

    @Value("${bearer-token}")
    protected String bearerToken;

    public Proxy(WebClient webClient) {
        this.webClient = webClient;
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

}
