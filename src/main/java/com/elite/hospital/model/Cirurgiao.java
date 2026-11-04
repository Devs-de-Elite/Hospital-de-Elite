package com.elite.hospital.model;

import jakarta.persistence.Entity;

@Entity
public class Cirurgiao extends Profissional {

    private String areaCirurgica;

    public String getAreaCirurgica() {
        return areaCirurgica;
    }

    public void setAreaCirurgica(String areaCirurgica) {
        this.areaCirurgica = areaCirurgica;
    }
}
