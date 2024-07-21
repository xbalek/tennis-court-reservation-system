package com.xbalek.tennis_court_reservation_system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(corsCustomizer -> corsCustomizer.disable())
			.authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/tennis-courts/**"
                ).permitAll()
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/surface-types/**"
                ).permitAll()
                .requestMatchers(
                    "/api/auth/**"
                ).permitAll()
                .anyRequest().authenticated()
			);
		return http.build();
    }

}
