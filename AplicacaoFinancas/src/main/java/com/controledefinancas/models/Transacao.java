package com.controledefinancas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // Mudado de 'type' para 'tipo'
    private double valor; // Mudado de 'amount' para 'valor'
    private String categoria; // Mudado de 'category' para 'categoria'
    private LocalDateTime data; // Mudado de 'date' para 'data'

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}