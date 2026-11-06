package com.elite.hospital.model;

import jakarta.persistence.Entity;

@Entity
public class Cirurgiao extends Profissional {

    private String atuacaoCirurgica;

    public String getAtuacaoCirurgica() {
        return atuacaoCirurgica;
    }

    public void setAtuacaoCirurgica(String atuacaoCirurgica) {
        this.atuacaoCirurgica = atuacaoCirurgica;
    }
}