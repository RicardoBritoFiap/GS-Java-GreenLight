package com.pedrosbm.GreenLight.consumo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.pedrosbm.GreenLight.lampada.Lampada;


public interface ConsumoRepository extends JpaRepository<Consumo, Long>{   
    public List<Consumo> findByLampada(Lampada lampada);
}