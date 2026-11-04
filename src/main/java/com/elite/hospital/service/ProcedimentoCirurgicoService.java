package com.elite.hospital.service;

import com.elite.hospital.enums.SituacaoProcedimento;
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

        return procedimentoRepository.findByProfissionalResponsavelIdAndSituacaoProcedimento(
                cirurgiaoId, SituacaoProcedimento.CONCLUIDO);
    }

    public ProcedimentoCirurgico marcarProcedimento(ProcedimentoCirurgico procedimento, Long pacienteId, Long cirurgiaoId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));
        Profissional cirurgiao = profissionalRepository.findById(cirurgiaoId)
                .orElseThrow(() -> new RuntimeException("Cirurgião não encontrado!"));

        procedimento.setPaciente(paciente);
        procedimento.setProfissionalResponsavel(cirurgiao);
        procedimento.validarCondicoes();
        procedimento.setSituacaoProcedimento(SituacaoProcedimento.AGENDADO);
        return procedimentoRepository.save(procedimento);
    }

    public ProcedimentoCirurgico iniciar(Long id) {
        ProcedimentoCirurgico procedimento = buscar(id);

        if (procedimento.getSituacaoProcedimento() != SituacaoProcedimento.AGENDADO) {
            throw new RuntimeException("Só é possível iniciar um procedimento agendado.");
        }

        procedimento.validarCondicoes();
        procedimento.setDataHoraEfetiva(LocalDateTime.now());
        procedimento.setSituacaoProcedimento(SituacaoProcedimento.EM_ANDAMENTO);
        return procedimentoRepository.save(procedimento);
    }

    public ProcedimentoCirurgico concluir(Long id, String observacoesPosOperatorias) {
        ProcedimentoCirurgico procedimento = buscar(id);

        if (procedimento.getSituacaoProcedimento() != SituacaoProcedimento.EM_ANDAMENTO) {
            throw new RuntimeException("Não é permitido concluir um procedimento que não foi iniciado.");
        }

        procedimento.setObservacoesPosOperatorias(observacoesPosOperatorias);
        procedimento.setSituacaoProcedimento(SituacaoProcedimento.CONCLUIDO);
        return procedimentoRepository.save(procedimento);
    }

    public ProcedimentoCirurgico cancelar(Long id) {
        ProcedimentoCirurgico procedimento = buscar(id);

        if (procedimento.getSituacaoProcedimento() == SituacaoProcedimento.CONCLUIDO
                || procedimento.getSituacaoProcedimento() == SituacaoProcedimento.CANCELADO) {
            throw new RuntimeException("Um procedimento concluído ou cancelado não pode ser cancelado.");
        }

        procedimento.setSituacaoProcedimento(SituacaoProcedimento.CANCELADO);
        return procedimentoRepository.save(procedimento);
    }

    private ProcedimentoCirurgico buscar(Long id) {
        return procedimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado!"));
    }
}
