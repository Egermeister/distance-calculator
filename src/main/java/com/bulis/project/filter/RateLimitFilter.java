package com.bulis.project.filter;

import com.bulis.project.exception.RequestLimitExceededException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Slf4j
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static final List<String> ENDPOINTS_TO_CHECK = List.of("/api/v1/distance/calculate");

    private final Map<String, RequestCounter> requestCountMap = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (ENDPOINTS_TO_CHECK.contains(requestUri)) {
            var ipAddress = request.getRemoteAddr();
            RequestCounter requestCounter = requestCountMap.computeIfAbsent(ipAddress, k -> new RequestCounter());

            if (requestCounter.shouldBlock()) {
                throw new RequestLimitExceededException();
            }
        }

        filterChain.doFilter(request, response);
    }

    @Data
    private class RequestCounter {
        private static final long ONE_MINUTE_INTERVAL_IN_SECONDS = 60;
        private int count;
        private long lastResetTime;

        public synchronized boolean shouldBlock() {
            long currentTime = Instant.now().getEpochSecond();
            if (currentTime - lastResetTime >= ONE_MINUTE_INTERVAL_IN_SECONDS) {
                // Reset counter and update the reset time
                count = 1;
                lastResetTime = currentTime;
            } else {
                count++;
            }

            return count > MAX_REQUESTS_PER_MINUTE;
        }
    }
}
