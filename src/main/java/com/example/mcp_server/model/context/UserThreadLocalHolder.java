package com.example.mcp_server.model.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedInheritableThreadLocal;

@Slf4j
public class UserThreadLocalHolder {
    public static final String MCP_USER_DATA = "MCP User Data";
    private static final ThreadLocal<UserDataModel> currentUser = new NamedInheritableThreadLocal<>(MCP_USER_DATA);

    public static void set(UserDataModel user) {
        currentUser.set(user);
    }

    public static UserDataModel get() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
