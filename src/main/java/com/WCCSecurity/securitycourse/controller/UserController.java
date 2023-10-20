package com.WCCSecurity.securitycourse.controller;

import com.WCCSecurity.securitycourse.config.JwtService;
import com.WCCSecurity.securitycourse.entity.Role;
import com.WCCSecurity.securitycourse.entity.User;
import com.WCCSecurity.securitycourse.repository.RoleRepository;
import com.WCCSecurity.securitycourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    // route accessible par tout le monde pour s'inscrire
    @PostMapping (value = "/register")
    public String register(){
        return "Formulaire d'inscription";
    }

    // route accessible par tout le monde pour se loger
    @GetMapping(value = "/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String role) {
        Optional<User> userOptional = this.userRepository.findByUsername(username);
        Optional<Role> roleOptional = this.roleRepository.findByRole(role);
        //on vérifie la présence du user et de son rôle
        if (userOptional.isPresent() && roleOptional.isPresent()) {
            String realUser = userOptional.get().getUsername();
            String realRole = roleOptional.get().getRole();
            String token = this.jwtService.generateToken(realUser, realRole);
            return ResponseEntity.ok(token);
        } else {
            // sinon on renvoi une erreur
            System.err.println("User or Role not found: " + username + " or " + role);
            throw new RuntimeException("User or Role not found");
        }
    }

    // route accessible aux users et admins
    @GetMapping(value = "/admin-user")
    public String dashboard(){
        return "Dashboard pour user et admin!";
    }

    //route accessible uniquement aux admin
    @GetMapping(value = "/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin(){
        return "Hello Admin !";
    }
}
