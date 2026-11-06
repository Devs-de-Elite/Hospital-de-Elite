package com.elite.hospital.model;

import com.elite.hospital.enums.SituacaoConsulta;
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

    @Override
    public void RealizarAtendimento(Atendimento atendimento, String observacoes) {
        validarResponsabilidade(atendimento);
        if (!(atendimento instanceof ProcedimentoCirurgico procedimento)) {
            throw new RuntimeException("O cirurgião realiza procedimentos cirúrgicos.");
        }
        procedimento.validarSituacao(SituacaoConsulta.CONCLUIDA);
        procedimento.setObservacoesPosOperatorias(observacoes);
        procedimento.setSituacao(SituacaoConsulta.CONCLUIDA);
    }
}
