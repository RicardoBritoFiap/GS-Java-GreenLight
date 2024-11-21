package com.pedrosbm.GreenLight.lampada;

import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pedrosbm.GreenLight.user.User;
import com.pedrosbm.GreenLight.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PutMapping;

@Controller
@Slf4j
public class LampadaController {
    private LampadaRepository repository;
    private UserRepository repository2;
    private RabbitTemplate rabbitTemplate;

    public LampadaController(LampadaRepository repository, UserRepository repository2, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.repository2 = repository2;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("form")
    public String form(Lampada lampada){
        return "form";
    }

    @GetMapping()
    public String getLampadas(Model model, @AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        
        List<Lampada> lampadas = repository.findByUserEmail(email);
        model.addAttribute("lampadas", lampadas);

        return "index";
    }
    
    @GetMapping("lampada/{id}")
    public String getLampada(@RequestParam Long id, Model model) {
        Lampada lampada = repository.findById(id).get();

        model.addAttribute("lampada", lampada);
        return "lampada";
    }

    @PostMapping("lampada")
    public String create( Lampada lampada, BindingResult result, RedirectAttributes redirect, @AuthenticationPrincipal OAuth2User principal){
        if (result.hasErrors()) return "form";

        String email = principal.getAttribute("email");
        User user = repository2.findByEmail(email).get();
        lampada.setUser(user);
        lampada.setModo("automatico");
        lampada.setEstado("apagada");

        repository.save(lampada);
        
        redirect.addFlashAttribute("message", "lampada cadastrada com sucesso");
        return "redirect:/";
    }
    
    @PutMapping("lampada/acender/{id}")
    public String acender(@PathVariable Long id, RedirectAttributes redirect,  @AuthenticationPrincipal OAuth2User principal) {
        Lampada lampada = repository.findById(id).get();
        lampada.setEstado("acesa");
        repository.save(lampada);
        String email = principal.getAttribute("email");

        Map<String, String> payload = Map.of(
            "email", email,
            "mensagem", "A sua lampada foi acesa!: " + lampada.getApelido()
        );

        rabbitTemplate.convertAndSend("email-queue", payload);

        return "redirect:/";
    }   
    
    @PutMapping("lampada/apagar/{id}")
    public String apagar(@PathVariable Long id, RedirectAttributes redirect) {
        Lampada lampada = repository.findById(id).get();
        lampada.setEstado("apagada");
        repository.save(lampada);
        
        return "redirect:/";
    }   

    @PutMapping("lampada/automatico/{id}")
    public String automatico(@PathVariable Long id, RedirectAttributes redirect) {
        Lampada lampada = repository.findById(id).get();
        lampada.setModo("automatico");
        repository.save(lampada);
        
        return "redirect:/";
    }   

    @PutMapping("lampada/manual/{id}")
    public String manual(@PathVariable Long id, RedirectAttributes redirect, @AuthenticationPrincipal OAuth2User principal) {
        Lampada lampada = repository.findById(id).get();
        lampada.setModo("manual");
        repository.save(lampada);
        String email = principal.getAttribute("email");

        Map<String, String> payload = Map.of(
            "email", email,
            "mensagem", "A sua lampada foi acesa!: " + lampada.getApelido()
        );
       
        rabbitTemplate.convertAndSend("email-queue", payload);

        return "redirect:/";
    }   

    @DeleteMapping("lampada/{id}")
    public String deleteLampada(@PathVariable Long id, RedirectAttributes redirect){
        repository.deleteById(id);

        return "redirect:/";
    }
}