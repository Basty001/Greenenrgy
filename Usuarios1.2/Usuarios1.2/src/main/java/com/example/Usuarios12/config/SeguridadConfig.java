package com.example.Usuarios12.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;



/**
 * Configuración de seguridad para la aplicación.
 * Define políticas de acceso, autenticación y encriptación.
 */
@Configuration
public class SeguridadConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configura el codificador de contraseñas BCrypt.
     * @return Instancia de BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la cadena de filtros de seguridad.
     * @param http Configuración de seguridad HTTP
     * @return SecurityFilterChain configurado
     * @throws Exception Si hay errores en la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers("/api/v1/usuario/login", "/api/v1/usuario/users").permitAll()
                
                // Swagger solo para administradores
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**")
                    .hasAuthority("Administrador") 

                .requestMatchers("/api/v1/usuario/login").permitAll()
                .requestMatchers("/api/v1/reportes/**").authenticated()  // Requiere login
                
                // Todos los demás endpoints requieren autenticación
                .anyRequest().authenticated()
            )
            .userDetailsService(customUserDetailsService) // Servicio de autenticación personalizado
            .httpBasic(withDefaults()); // Habilita autenticación básica HTTP
        
        return http.build();
    }
}