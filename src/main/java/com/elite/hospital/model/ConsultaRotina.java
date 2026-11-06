package com.elite.hospital.model;

import jakarta.persistence.Entity;

@Entity
public class ConsultaRotina extends Atendimento {

    private String motivo;
    private String observacoes;

    public String getMotivo() {
        return motivo; 
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo; 
    }
    public String getObservacoes() {
        return observacoes; 
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes; 
    }
    @Override
    public void validarCondicoes() {
        super.validarCondicoes();
        if (!(getProfissionalResponsavel() instanceof MedicoClinicoGeral)) {
            throw new RuntimeException("A consulta de rotina deve ter um clínico geral como responsável.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new RuntimeException("A consulta de rotina deve informar o motivo.");
        }
    }
}
