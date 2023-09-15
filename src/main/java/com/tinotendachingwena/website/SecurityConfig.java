package com.tinotendachingwena.website;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .requiresChannel(channelRequestMatcherRegistry -> channelRequestMatcherRegistry
                        .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                        .requiresSecure())
                .headers().frameOptions().sameOrigin();

        return http.build();
    }

}