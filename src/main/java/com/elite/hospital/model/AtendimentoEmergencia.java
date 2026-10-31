package com.elite.hospital.model;

import com.elite.hospital.enums.PrioridadeAtendimento;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class AtendimentoEmergencia extends Atendimento {

    @Enumerated(EnumType.STRING)
    private PrioridadeAtendimento prioridade;

    public PrioridadeAtendimento getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(PrioridadeAtendimento prioridade) {
        this.prioridade = prioridade;
    }
}