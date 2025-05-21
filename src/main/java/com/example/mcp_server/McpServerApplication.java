package com.example.mcp_server;

import com.example.mcp_server.service.DateTimeService;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);
	}

	@Bean
	public List<ToolCallback> dateTimeTools(DateTimeService dateTimeService) {
		return List.of(ToolCallbacks.from(dateTimeService));
	}
}
