package com.elite.hospital.model;

import com.elite.hospital.enums.Especialidade;
import jakarta.persistence.*;

@Entity
public class MedicoEspecialista extends Profissional {

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
