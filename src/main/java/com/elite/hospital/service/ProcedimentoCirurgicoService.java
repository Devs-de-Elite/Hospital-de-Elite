package com.elite.hospital.service;

import com.elite.hospital.enums.SituacaoConsulta;
import com.elite.hospital.model.Cirurgiao;
import com.elite.hospital.model.Paciente;
import com.elite.hospital.model.Profissional;
import com.elite.hospital.model.ProcedimentoCirurgico;
import com.elite.hospital.repository.PacienteRepository;
import com.elite.hospital.repository.ProcedimentoCirurgicoRepository;
import com.elite.hospital.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProcedimentoCirurgicoService {

    @Autowired
    private ProcedimentoCirurgicoRepository procedimentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<ProcedimentoCirurgico> listarTodos() {
        return procedimentoRepository.findAll();
    }

    public List<ProcedimentoCirurgico> listarRealizados(Long cirurgiaoId) {
        Profissional profissional = profissionalRepository.findById(cirurgiaoId)
                .orElseThrow(() -> new RuntimeException("Cirurgião não encontrado!"));

        if (!(profissional instanceof Cirurgiao)) {
            throw new RuntimeException("O profissional informado não é um cirurgião.");
        }

        return procedimentoRepository.findByProfissionalResponsavelIdAndSituacao(
                cirurgiaoId, SituacaoConsulta.CONCLUIDA);
    }

    public ProcedimentoCirurgico marcarProcedimento(ProcedimentoCirurgico procedimento, Long pacienteId, Long cirurgiaoId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));
        Profissional cirurgiao = profissionalRepository.findById(cirurgiaoId)
                .orElseThrow(() -> new RuntimeException("Cirurgião não encontrado!"));

        procedimento.setPaciente(paciente);
        procedimento.setProfissionalResponsavel(cirurgiao);
        procedimento.setSituacao(null);
        procedimento.validarCondicoes();
        procedimento.validarSituacao(SituacaoConsulta.AGENDADA);
        procedimento.setSituacao(SituacaoConsulta.AGENDADA);
        return procedimentoRepository.save(procedimento);
    }

    public ProcedimentoCirurgico iniciar(Long id) {
        ProcedimentoCirurgico procedimento = buscar(id);

        procedimento.validarSituacao(SituacaoConsulta.EM_ATENDIMENTO);
        procedimento.validarCondicoes();
        procedimento.setDataHoraEfetiva(LocalDateTime.now());
        procedimento.setSituacao(SituacaoConsulta.EM_ATENDIMENTO);
        return procedimentoRepository.save(procedimento);
    }

    public ProcedimentoCirurgico concluir(Long id, String observacoesPosOperatorias) {
        ProcedimentoCirurgico procedimento = buscar(id);

        procedimento.validarSituacao(SituacaoConsulta.CONCLUIDA);
        procedimento.setObservacoesPosOperatorias(observacoesPosOperatorias);
        procedimento.setSituacao(SituacaoConsulta.CONCLUIDA);
        return procedimentoRepository.save(procedimento);
    }

    public ProcedimentoCirurgico cancelar(Long id) {
        ProcedimentoCirurgico procedimento = buscar(id);

        procedimento.validarSituacao(SituacaoConsulta.CANCELADA);
        procedimento.setSituacao(SituacaoConsulta.CANCELADA);
        return procedimentoRepository.save(procedimento);
    }

    private ProcedimentoCirurgico buscar(Long id) {
        return procedimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado!"));
    }
}