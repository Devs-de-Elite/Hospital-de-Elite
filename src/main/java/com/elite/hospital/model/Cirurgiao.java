package com.elite.hospital.model;

import java.util.ArrayList;

public class Cirurgiao extends Profissional{
    private String atuacaoCirurgica;
    private ArrayList<ProcedimentoCirurgico> listaProcedimentos;

    public void RealizarAtendimento(ProcedimentoCirurgico procedimento, String observacao) {
        if (procedimento.situacao == SituacaoProcedimento.AGENDADO) {
            procedimento.observacoes = observacoes;
            listaProcedimentos.add(procedimento);
            procedimento.situacao = SituacaoProcedimento.CONCLUIDA;
        } else {
            System.out.println("Essa procedimento já foi ou não pode ser realizado");
        }
    }
}
