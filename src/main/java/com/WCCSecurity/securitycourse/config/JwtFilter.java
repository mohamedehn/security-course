package com.WCCSecurity.securitycourse.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter { //le filter est exécuté a chaque requête

    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //extrait la valeur de l'en-tête "Authorization" de la requête HTTP, qui doit contenir le jeton JWT.
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            //on spécifie 7 car bearer = 6 lettres + espace, on vérifie donc après bearer
            String token = authorizationHeader.substring(7);

            if (token != null && this.jwtService.isValid(token, this.jwtService.getSubject(token))) {

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(this.jwtService.getRole(token)));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        this.jwtService.getSubject(token),
                        null,
                        authorities // Roles qui permettent à ma config d'accepter la requête de l'utilisateur
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
