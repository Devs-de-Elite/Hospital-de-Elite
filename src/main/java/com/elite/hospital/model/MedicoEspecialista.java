package com.elite.hospital.model;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.enums.SituacaoConsulta;
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

    @Override
    public void RealizarAtendimento(Atendimento atendimento, String observacoes) {
        validarResponsabilidade(atendimento);
        if (atendimento instanceof ConsultaEspecializada consulta) {
            if (consulta.getSituacao() == SituacaoConsulta.CONFIRMADA) {
                consulta.setSituacao(SituacaoConsulta.EM_ATENDIMENTO);
            }
            if (consulta.getSituacao() != SituacaoConsulta.EM_ATENDIMENTO) {
                throw new RuntimeException("A consulta não está em atendimento.");
            }
            consulta.setObservacoes(observacoes);
            consulta.setSituacao(SituacaoConsulta.CONCLUIDA);
        } else if (atendimento instanceof AtendimentoEmergencia) {
            if (atendimento.getSituacao() != SituacaoConsulta.EM_ATENDIMENTO) {
                throw new RuntimeException("A emergência não foi iniciada.");
            }
            atendimento.setSituacao(SituacaoConsulta.CONCLUIDA);
        } else {
            throw new RuntimeException("O especialista não realiza esta modalidade de atendimento.");
        }
    }
}
