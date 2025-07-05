package com.example.Notificaciones1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/notificaciones/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/notificaciones/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults()); // Autenticación básica
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails usuarioNormal = User.builder()
            .username("user")
            .password("{noop}password") // Contraseña: password
            .roles("USER")
            .build();
        
        UserDetails adminVictor = User.builder()
            .username("victor")
            .password("{noop}12345") // Contraseña: 12345
            .roles("ADMIN")
            .build();
            
        return new InMemoryUserDetailsManager(usuarioNormal, adminVictor);
    }
}

//InMemoryUserDetailsManager, withUsername