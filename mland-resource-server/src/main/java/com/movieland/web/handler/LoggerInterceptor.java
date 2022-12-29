package com.movieland.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.UUID;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UUID requestId = UUID.randomUUID();
        Principal principal = request.getUserPrincipal();

        MDC.put("requestId", requestId.toString());
        MDC.put("user", principal == null ? "guest" : principal.getName());
        MDC.put("path", request.getRequestURI());
        MDC.put("method", request.getMethod());
//        MDC.put("logging.pattern.level", "%5p [${spring.zipkin.service.name:" + "${spring.application.name:}},%X{traceId:-},%X{spanId:-}]");
        return true;
    }

}
