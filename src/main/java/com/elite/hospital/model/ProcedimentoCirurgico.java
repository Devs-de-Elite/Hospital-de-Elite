package com.elite.hospital.model;

import com.elite.hospital.enums.SituacaoProcedimento;
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

    @Enumerated(EnumType.STRING)
    private SituacaoProcedimento situacaoProcedimento;

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

    public SituacaoProcedimento getSituacaoProcedimento() {
        return situacaoProcedimento;
    }

    public void setSituacaoProcedimento(SituacaoProcedimento situacaoProcedimento) {
        this.situacaoProcedimento = situacaoProcedimento;
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
}
