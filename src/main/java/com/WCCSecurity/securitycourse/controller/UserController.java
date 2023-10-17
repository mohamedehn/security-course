package com.WCCSecurity.securitycourse.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // route accessible par tout le monde pour s'inscrire
    @PostMapping (value = "/register")
    public String register(){
        return "Formulaire d'inscription";
    }

    // route accessible par tout le monde pour se loger
    @PostMapping(value = "/login")
    public String login(){
        return "Se loger";
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
