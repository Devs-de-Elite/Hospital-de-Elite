package com.elite.hospital.model;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.enums.SituacaoConsulta;
import jakarta.persistence.*;

@Entity
public class ConsultaEspecializada extends Atendimento {

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private String observacoes;

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
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
        if (especialidade == null) {
            throw new RuntimeException("A consulta especializada deve informar a especialidade.");
        }
        if (!(getProfissionalResponsavel() instanceof MedicoEspecialista especialista)) {
            throw new RuntimeException("A consulta especializada deve ser realizada por um médico especialista.");
        }
        if (especialista.getEspecialidade() != especialidade) {
            throw new RuntimeException("A especialidade do profissional não é compatível com o atendimento solicitado.");
        }
    }

    public void validarSituacao(SituacaoConsulta novaSituacao) {
        SituacaoConsulta situacaoAtual = getSituacao();

        if (situacaoAtual == null) {
            if (novaSituacao != SituacaoConsulta.AGENDADA) {
                throw new RuntimeException("Uma consulta nova deve começar como AGENDADA.");
            }
            return;
        }

        boolean permitido = switch (situacaoAtual) {
            case AGENDADA -> novaSituacao == SituacaoConsulta.CONFIRMADA
                    || novaSituacao == SituacaoConsulta.CANCELADA
                    || novaSituacao == SituacaoConsulta.NAO_COMPARECEU;
            case CONFIRMADA -> novaSituacao == SituacaoConsulta.EM_ATENDIMENTO
                    || novaSituacao == SituacaoConsulta.CANCELADA
                    || novaSituacao == SituacaoConsulta.NAO_COMPARECEU;
            case EM_ATENDIMENTO -> novaSituacao == SituacaoConsulta.CONCLUIDA;
            default -> false;
        };

        if (!permitido) {
            throw new RuntimeException("Não é possível alterar a consulta de " + situacaoAtual
                    + " para " + novaSituacao + ". Consultas concluídas, canceladas ou sem comparecimento exigem um novo agendamento.");
        }
    }
}