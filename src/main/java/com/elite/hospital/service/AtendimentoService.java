package com.elite.hospital.service;

import com.elite.hospital.model.Atendimento;
import com.elite.hospital.enums.SituacaoConsulta;
import com.elite.hospital.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elite.hospital.model.ConsultaRotina;

import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    public List<Atendimento> listarTodos() {
        return atendimentoRepository.findAll();
    }

    public Atendimento marcarAtendimento(Atendimento atendimento) {
        atendimento.setSituacao(SituacaoConsulta.AGENDADA);
        return atendimentoRepository.save(atendimento);
    }

    public Atendimento atualizarSituacao(Long id, SituacaoConsulta novaSituacao) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado!"));

        SituacaoConsulta situacaoAtual = atendimento.getSituacao();

        if (situacaoAtual == SituacaoConsulta.CONCLUIDA) {
            throw new RuntimeException("Uma consulta concluída não pode ter a sua situação alterada.");
        }

        if (situacaoAtual == SituacaoConsulta.CANCELADA && novaSituacao == SituacaoConsulta.CONCLUIDA) {
            throw new RuntimeException("Uma consulta cancelada não pode ser concluída sem um novo agendamento.");
        }

        atendimento.setSituacao(novaSituacao);
        return atendimentoRepository.save(atendimento);
    }

    public Atendimento adicionarObservacao(Long Id, String textoObs){
        Atendimento atendimento = atendimentoRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));
        
        if (!(atendimento instanceof ConsultaRotina)) {
            throw new RuntimeException("Apenas consultas de rotina podem ter o registo de observações.");
        }

        ConsultaRotina consulta = (ConsultaRotina) atendimento;

        
        if (consulta.getSituacao() != SituacaoConsulta.CONCLUIDA) {
            throw new RuntimeException("Só é possível registar observações após a conclusão da consulta.");
        }

        consulta.setObservacoes(textoObs);
        return atendimentoRepository.save(consulta);
    }
}
