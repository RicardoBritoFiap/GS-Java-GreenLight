package com.pedrosbm.GreenLight.lampada;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LampadaRepository extends JpaRepository<Lampada, Long> {
    public List<Lampada> findByUserEmail(String email);
}
