package com.lisaanna.ws_todo.security;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class SecurityConfig {

    @Bean
    public RateLimiter rateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(2) // Max requests per interval
                .limitRefreshPeriod(Duration.ofMinutes(1)) // Interval duration
                .timeoutDuration(Duration.ofSeconds(1)) // Timeout for acquiring permits
                .build();
        return RateLimiter.of("myRateLimiter", config);
    }
}
