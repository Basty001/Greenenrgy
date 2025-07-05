package com.example.Soporte.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;





@Configuration
public class SecurityConfig {

    /**
     * Configuración de usuario en memoria para autenticación básica
     * Usuario: victor
     * Contraseña: 12345
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("victor")
            .password("12345")
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Configuración del filtro de seguridad
     * - Deshabilita CSRF
     * - Requiere autenticación básica en todas las peticiones
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .httpBasic();
        return http.build();
    }
}