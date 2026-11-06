package com.elite.hospital.service;

import com.elite.hospital.model.Internacao;
import com.elite.hospital.model.Quarto;
import com.elite.hospital.enums.SituacaoInternacao;
import com.elite.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class InternacaoService {
    @Autowired
    private InternacaoRepository internacaoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ProfissionalRepository profissionalRepository;
    @Autowired
    private QuartoRepository quartoRepository;

    public List<Internacao> listarTodas() {
        return internacaoRepository.findAll();
    }

    @Transactional
    public Internacao internar(Internacao internacao, Long pacienteId, Long profissionalId, Long quartoId) {
        if (internacao.getId() != null || internacao.getDataAlta() != null
                || (internacao.getSituacao() != null && internacao.getSituacao() != SituacaoInternacao.ATIVA)) {
            throw new RuntimeException("Uma nova internação deve iniciar ativa e sem alta.");
        }
        internacao.setPaciente(pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!")));
        internacao.setProfissionalResponsavel(profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!")));
        Quarto quarto = quartoRepository.findById(quartoId)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado!"));
        quarto.validarCondicoes();
        if (!quarto.possuiVaga(internacaoRepository.countByQuartoIdAndSituacao(quartoId, SituacaoInternacao.ATIVA))) {
            throw new RuntimeException("O quarto não possui vaga.");
        }
        internacao.setQuarto(quarto);
        internacao.validarCondicoes();
        internacao.setSituacao(SituacaoInternacao.ATIVA);
        return internacaoRepository.save(internacao);
    }

    @Transactional
    public Internacao registrarAlta(Long id, LocalDate dataAlta) {
        Internacao internacao = internacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internação não encontrada!"));
        internacao.registrarAlta(dataAlta);
        return internacaoRepository.save(internacao);
    }
}
