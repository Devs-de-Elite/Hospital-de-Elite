package com.elite.hospital.model;

import com.elite.hospital.enums.SituacaoConsulta;
import com.elite.hospital.enums.TipoCirurgia;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProcedimentoCirurgico extends Atendimento {

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoCirurgia tipoCirurgia;

    private String observacoesPreOperatorias;
    private String observacoesPosOperatorias;
    private LocalDateTime dataHoraEfetiva;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoCirurgia getTipoCirurgia() {
        return tipoCirurgia;
    }

    public void setTipoCirurgia(TipoCirurgia tipoCirurgia) {
        this.tipoCirurgia = tipoCirurgia;
    }

    public String getObservacoesPreOperatorias() {
        return observacoesPreOperatorias;
    }

    public void setObservacoesPreOperatorias(String observacoesPreOperatorias) {
        this.observacoesPreOperatorias = observacoesPreOperatorias;
    }

    public String getObservacoesPosOperatorias() {
        return observacoesPosOperatorias;
    }

    public void setObservacoesPosOperatorias(String observacoesPosOperatorias) {
        this.observacoesPosOperatorias = observacoesPosOperatorias;
    }

    public LocalDateTime getDataHoraEfetiva() {
        return dataHoraEfetiva;
    }

    public void setDataHoraEfetiva(LocalDateTime dataHoraEfetiva) {
        this.dataHoraEfetiva = dataHoraEfetiva;
    }

    @Override
    public void validarCondicoes() {
        super.validarCondicoes();
        if (!(getProfissionalResponsavel() instanceof Cirurgiao)) {
            throw new RuntimeException("O procedimento cirúrgico deve ter um cirurgião como responsável.");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new RuntimeException("O procedimento cirúrgico deve possuir descrição.");
        }
        if (tipoCirurgia == null) {
            throw new RuntimeException("O procedimento cirúrgico deve informar o tipo de cirurgia.");
        }
    }

    public void validarSituacao(SituacaoConsulta novaSituacao) {
        SituacaoConsulta situacaoAtual = getSituacao();
        if (novaSituacao == null) {
            throw new RuntimeException("A situação deve ser informada.");
        }

        if (novaSituacao == SituacaoConsulta.CONFIRMADA || novaSituacao == SituacaoConsulta.NAO_COMPARECEU) {
            throw new RuntimeException("Um procedimento cirúrgico não pode ficar como " + novaSituacao + ".");
        }

        if (situacaoAtual == null) {
            if (novaSituacao != SituacaoConsulta.AGENDADA) {
                throw new RuntimeException("Um procedimento cirúrgico novo deve começar como AGENDADA.");
            }
            return;
        }

        if (novaSituacao == SituacaoConsulta.CONCLUIDA
                && (situacaoAtual != SituacaoConsulta.EM_ATENDIMENTO || dataHoraEfetiva == null)) {
            throw new RuntimeException("Não é permitido concluir um procedimento que não foi iniciado.");
        }

        boolean permitido = switch (situacaoAtual) {
            case AGENDADA -> novaSituacao == SituacaoConsulta.EM_ATENDIMENTO
                    || novaSituacao == SituacaoConsulta.CANCELADA;
            case EM_ATENDIMENTO -> novaSituacao == SituacaoConsulta.CONCLUIDA
                    || novaSituacao == SituacaoConsulta.CANCELADA;
            default -> false;
        };

        if (!permitido) {
            throw new RuntimeException("Não é possível alterar o procedimento de " + situacaoAtual
                    + " para " + novaSituacao + ".");
        }
    }
}
