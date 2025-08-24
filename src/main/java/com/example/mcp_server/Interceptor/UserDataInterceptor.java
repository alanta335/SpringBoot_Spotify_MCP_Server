package com.example.mcp_server.Interceptor;

import com.example.mcp_server.model.context.UserDataModel;
import com.example.mcp_server.model.context.UserThreadLocalHolder;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class UserDataInterceptor implements HandlerInterceptor {

    public static final String USER_NAME_HEADER = "X-User-Name-Header";
    public static final String USER_ID_HEADER = "X-User-Id-Header";
    public static final String USER_API_KEY_HEADER = "X-User-API-Key-Header";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String userName = request.getHeader(USER_NAME_HEADER);
        String userId = request.getHeader(USER_ID_HEADER);
        String userAPIKey = request.getHeader(USER_API_KEY_HEADER);

        if (StringUtils.isBlank(userAPIKey)) {
            log.atError()
                    .addKeyValue("userName", userName)
                    .addKeyValue("userId", userId)
                    .addKeyValue("userAPIKey", userAPIKey)
                    .log("User data headers are missing in the request");
            return true;
        }

        try {
            UserThreadLocalHolder.set(new UserDataModel(userName, userId, userAPIKey));
        } catch (Exception e) {
            log.atError()
                    .setCause(e)
                    .log("Failed to set user data in thread local");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserThreadLocalHolder.clear();
    }
}