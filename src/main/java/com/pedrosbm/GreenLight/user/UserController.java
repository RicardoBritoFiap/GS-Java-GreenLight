package com.pedrosbm.GreenLight.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/user")
    public String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
        String nome = principal.getAttribute("name");
        String email = principal.getAttribute("email");

        User user = repository.findByEmail(email)
        .orElseGet(() -> {
            User newUser = new User(nome, email);
            return repository.save(newUser);
        });
        
        model.addAttribute("user", user);
        return "user";
    }

    @PutMapping("/user")
    public User updateClienteAutenticado(@AuthenticationPrincipal OAuth2User principal,@RequestBody User usuarioAtualizado) {

        String email = principal.getAttribute("email");
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user não encontrado"));

        user.setNome(usuarioAtualizado.getNome());
        
        return repository.save(user);
    }
}