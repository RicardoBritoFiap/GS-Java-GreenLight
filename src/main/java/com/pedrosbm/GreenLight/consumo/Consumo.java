package com.pedrosbm.GreenLight.consumo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.pedrosbm.GreenLight.lampada.Lampada;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumoId;

    private Float consumoWh;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate mesConsumo;

    @ManyToOne
    @JoinColumn(name = "lampada_id")
    private Lampada lampada;
}