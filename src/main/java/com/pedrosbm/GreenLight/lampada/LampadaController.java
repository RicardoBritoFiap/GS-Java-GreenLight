package com.pedrosbm.GreenLight.lampada;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class LampadaController {
    @Autowired
    private LampadaRepository repository;

    @GetMapping()
    public String getLampadas(Model model, @AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        
        List<Lampada> lampadas = repository.findByUserEmail(email);
        List<Lampada> lampadasAll = repository.findAll();
        log.info("lampadas: " + lampadas);
        log.info("Todas: " + lampadasAll);
        model.addAttribute("lampadas", lampadas);

        return "index";
    }
    
    @GetMapping("lampada/{id}")
    public String getLampada(@RequestParam Long id, Model model) {
        Lampada lampada = repository.findById(id).get();

        model.addAttribute("lampada", lampada);
        return "lampada";
    }
    
    @PutMapping("lampada")
    public String novaLampada(@RequestBody @Valid Lampada lampada, RedirectAttributes redirect) {
        Boolean exists = repository.existsById(lampada.getLampadaId());

        if(exists){
            repository.save(lampada);
            redirect.addFlashAttribute("message", "Lampada editada com sucesso");
        }
        
        return "redirect:/";
    }   

    @DeleteMapping("lampada/{id}")
    public String deleteLampada(@PathVariable Long id, RedirectAttributes redirect){
        repository.deleteById(id);

        return "redirect:/";
    }
}