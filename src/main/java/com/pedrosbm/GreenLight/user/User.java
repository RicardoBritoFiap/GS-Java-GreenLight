package com.pedrosbm.GreenLight.user;

import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String nome;
    private String email;
    
    public User(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public User(OAuth2User principal){
        this.nome = principal.getAttribute("name");
        this.email = principal.getAttribute("email");
    }
}