package com.example.mcp_server.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class DateTimeService {
    @Tool(name = "getCurrentDateTime", description = "Get the current date and time")
    public String getCurrentDateTime() {
        return java.time.LocalDateTime.now().toString();
    }
}
