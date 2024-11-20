package com.pedrosbm.GreenLight.consumo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pedrosbm.GreenLight.lampada.Lampada;
import com.pedrosbm.GreenLight.lampada.LampadaRepository;

@Controller
@RequestMapping
public class ConsumoController {
    private ConsumoRepository repository;
    private LampadaRepository repository2;
    
    public ConsumoController(ConsumoRepository repository, LampadaRepository repository2) {
        this.repository = repository;
        this.repository2 = repository2;
    }

    @GetMapping("consumos")
    public String getConsumos(Model model, @AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        
        List<Lampada> lampadas = repository2.findByUserEmail(email);
        
        List<Consumo> consumos = new ArrayList<Consumo>();

        for(Lampada lampada : lampadas){
            consumos.addAll(repository.findByLampada(lampada));
        }

        model.addAttribute("consumos", consumos);

        return "consumos";
    }
}