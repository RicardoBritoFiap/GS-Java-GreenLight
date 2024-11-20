package com.pedrosbm.GreenLight.lampada;

import com.pedrosbm.GreenLight.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Lampada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lampadaId;

    @NotNull
    private String nomeDispositivo;

    @NotNull
    private String apelido;
    private String estado;
    private String modo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
