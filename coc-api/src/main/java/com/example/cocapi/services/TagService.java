package com.example.cocapi.services;

import org.springframework.stereotype.Service;

@Service
public class TagService {
    // Adds a # in front of the tag if there isn't one
    public String prepTag(String tag) {
        return tag.startsWith("#") ? tag : "#" + tag;
    }
}
