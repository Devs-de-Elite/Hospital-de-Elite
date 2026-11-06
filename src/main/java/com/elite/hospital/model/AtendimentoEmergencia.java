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
    @Override
    public void validarCondicoes() {
        super.validarCondicoes();
        if (prioridade == null) {
            throw new RuntimeException("A emergência deve informar a prioridade.");
        }
    }

    @Override
    public void validarSituacao(com.elite.hospital.enums.SituacaoConsulta novaSituacao) {
        if (getSituacao() == com.elite.hospital.enums.SituacaoConsulta.AGENDADA
                && novaSituacao == com.elite.hospital.enums.SituacaoConsulta.EM_ATENDIMENTO) {
            return;
        }
        super.validarSituacao(novaSituacao);
    }
}
