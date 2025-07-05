package com.example.resena1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/resenas/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = withDefaultPasswordEncoder()
            .username("admin")
            .password("12345")
            .roles("ADMIN")
            .build();
        
        UserDetails user = withDefaultPasswordEncoder()
            .username("user")
            .password("12345")
            .roles("USER")
            .build();

        UserDetails victor = withDefaultPasswordEncoder()
            .username("victor")
            .password("12345")
            .roles("ADMIN")
            .build();
            
        return new InMemoryUserDetailsManager(admin, user, victor);
    }
}