package com.elite.hospital.model;

import jakarta.persistence.*;
import com.elite.hospital.enums.TipoQuarto;

@Entity
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private int andar;
    private int capacidadeMax;
    @Enumerated(EnumType.STRING)
    private TipoQuarto tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public int getCapacidadeMax() {
        return capacidadeMax;
    }

    public void setCapacidadeMax(int capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public void validarCondicoes() {
        if (tipo == null || capacidadeMax <= 0) {
            throw new RuntimeException("Informe o tipo e uma capacidade positiva para o quarto.");
        }
    }

    public boolean possuiVaga(long ocupacaoAtual) {
        return ocupacaoAtual < capacidadeMax;
    }
}
