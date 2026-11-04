package com.elite.hospital.service;

import com.elite.hospital.enums.SituacaoConsulta;
import com.elite.hospital.model.ConsultaEspecializada;
import com.elite.hospital.model.Paciente;
import com.elite.hospital.model.Profissional;
import com.elite.hospital.repository.ConsultaEspecializadaRepository;
import com.elite.hospital.repository.PacienteRepository;
import com.elite.hospital.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaEspecializadaService {

    @Autowired
    private ConsultaEspecializadaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<ConsultaEspecializada> listarTodas() {
        return consultaRepository.findAll();
    }

    public ConsultaEspecializada marcarConsulta(ConsultaEspecializada consulta, Long pacienteId, Long profissionalId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!"));

        consulta.setPaciente(paciente);
        consulta.setProfissionalResponsavel(profissional);
        consulta.validarCondicoes();
        consulta.setSituacao(SituacaoConsulta.AGENDADA);
        return consultaRepository.save(consulta);
    }

    public ConsultaEspecializada atualizarSituacao(Long id, SituacaoConsulta novaSituacao) {
        ConsultaEspecializada consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));

        SituacaoConsulta situacaoAtual = consulta.getSituacao();

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
            throw new RuntimeException("Não é possível alterar a consulta de " + situacaoAtual + " para " + novaSituacao + ".");
        }

        if (novaSituacao == SituacaoConsulta.EM_ATENDIMENTO) {
            consulta.validarCondicoes();
        }

        consulta.setSituacao(novaSituacao);
        return consultaRepository.save(consulta);
    }

    public ConsultaEspecializada registrarObservacoes(Long id, String observacoes) {
        ConsultaEspecializada consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));

        if (consulta.getSituacao() != SituacaoConsulta.EM_ATENDIMENTO
                && consulta.getSituacao() != SituacaoConsulta.CONCLUIDA) {
            throw new RuntimeException("Observações só podem ser registradas durante ou após a consulta.");
        }

        consulta.setObservacoes(observacoes);
        return consultaRepository.save(consulta);
    }
}
