package com.elite.hospital.model;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.enums.SituacaoConsulta;
import jakarta.persistence.Entity;
import java.util.ArrayList;

@Entity
public class MedicoClinicoGeral extends Profissional {

    @Override
    public void RealizarAtendimento(Atendimento atendimento, String observacoes) {
        validarResponsabilidade(atendimento);
        if (atendimento instanceof ConsultaRotina consulta) {
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
            throw new RuntimeException("O clínico geral não realiza esta modalidade de atendimento.");
        }
    }

    public MedicoEspecialista encaminharPaciente(Paciente paciente,
            ArrayList<MedicoEspecialista> listaMedicos, String especializacao) {
        if (paciente == null) {
            throw new RuntimeException("O paciente deve ser informado.");
        }
        Especialidade especialidade = Especialidade.valueOf(especializacao.toUpperCase(java.util.Locale.ROOT));
        for (MedicoEspecialista medico : listaMedicos) {
            if (medico.getEspecialidade() == especialidade && medico.isDisponivel()) {
                return medico;
            }
        }
        throw new RuntimeException("Nenhum especialista disponível para a área informada.");
    }
}
