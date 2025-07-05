package com.MyPackage.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeConfig {

    @Value("${youtube.api.url}")
    private String apiUrl;

    @Value("${youtube.api.key}")
    private String apiKey;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }
}