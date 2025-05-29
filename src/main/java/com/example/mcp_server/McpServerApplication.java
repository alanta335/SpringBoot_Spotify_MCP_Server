package com.example.mcp_server;

import com.example.mcp_server.service.DateTimeService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider dateTime(DateTimeService dateTimeService) {
        return MethodToolCallbackProvider.builder().toolObjects(dateTimeService).build();
    }
}
