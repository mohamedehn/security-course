package com.WCCSecurity.securitycourse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register", "login").permitAll() // accessible sans authentification
                        .requestMatchers(HttpMethod.GET,"/admin").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/admin-user").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                //permet d'ajouter la protection CSRF contre les attaques qui exploitent l'exécution non autorisée de commandes
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/register", "/login")
                )
                //on ajoute notre jwtfilter pour filtrer nos requêtes
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
