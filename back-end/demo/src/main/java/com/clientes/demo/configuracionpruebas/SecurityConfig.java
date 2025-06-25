package com.clientes.demo.configuracionpruebas;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*IMPORTANTE ESTO SOLO ES PARA PRUEBAS
 * 
 * POR HABER AGREGADO SPRING-BOOT-STARTER-SECURITY (DEPENDENCIA)
 * 
 * SE ACTIVA AUTOMÁTICAMENTE  PROTEGE TODAS LAS RUTAS POR DEFECTO
 */

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // Permite todas las rutas
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
