package com.elite.hospital.service;

import com.elite.hospital.model.Atendimento;
import com.elite.hospital.enums.SituacaoConsulta;
import com.elite.hospital.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elite.hospital.model.*;
import com.elite.hospital.repository.PacienteRepository;
import com.elite.hospital.repository.ProfissionalRepository;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<Atendimento> listarTodos() {
        return atendimentoRepository.findAll();
    }

    public Atendimento marcarAtendimento(Atendimento atendimento, Long pacienteId, Long profissionalId) {
        if (atendimento.getId() != null) {
            throw new RuntimeException("Um novo atendimento não deve informar ID.");
        }
        atendimento.setPaciente(pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!")));
        atendimento.setProfissionalResponsavel(profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!")));
        atendimento.validarCondicoes();
        if (atendimento.getSituacao() == null) {
            atendimento.setSituacao(SituacaoConsulta.AGENDADA);
        } else if (atendimento.getSituacao() != SituacaoConsulta.AGENDADA) {
            throw new RuntimeException("Um novo atendimento deve começar como AGENDADA.");
        }
        if (atendimento instanceof ProcedimentoCirurgico procedimento) {
            procedimento.setDataHoraEfetiva(null);
        }
        return atendimentoRepository.save(atendimento);
    }

    public Atendimento atualizarSituacao(Long id, SituacaoConsulta novaSituacao) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado!"));

        atendimento.validarSituacao(novaSituacao);
        if (novaSituacao == SituacaoConsulta.EM_ATENDIMENTO) {
            atendimento.validarCondicoes();
            if (atendimento instanceof ProcedimentoCirurgico procedimento) {
                procedimento.setDataHoraEfetiva(LocalDateTime.now());
            }
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

    public List<Atendimento> listarAguardandoAvaliacao() {
        return atendimentoRepository.findBySituacaoIn(
            List.of(SituacaoConsulta.AGENDADA, SituacaoConsulta.CONFIRMADA)
        );
    }

    public List<Atendimento> listarAvaliados() {
        return atendimentoRepository.findBySituacaoIn(
                List.of(SituacaoConsulta.EM_ATENDIMENTO, SituacaoConsulta.CONCLUIDA));
    }
    
}
