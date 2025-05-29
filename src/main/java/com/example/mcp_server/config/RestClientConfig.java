package com.example.mcp_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient(RestClient.Builder builder, @Value("${spotify.api.token}") String spotifyApiToken) {
        return builder
                .baseUrl("https://api.spotify.com/")
                .defaultHeader("Authorization", "Bearer " + spotifyApiToken)
                .build();
    }

}
