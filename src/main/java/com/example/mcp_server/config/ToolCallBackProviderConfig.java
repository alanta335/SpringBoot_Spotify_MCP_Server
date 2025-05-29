package com.example.mcp_server.config;

import com.example.mcp_server.service.DateTimeService;
import com.example.mcp_server.service.SpotifyService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallBackProviderConfig {
    @Bean
    public ToolCallbackProvider dateTime(DateTimeService dateTimeService, SpotifyService spotifyService) {
        return MethodToolCallbackProvider.builder().toolObjects(dateTimeService, spotifyService).build();
    }
}
