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
        if (consulta.getId() != null) {
            throw new RuntimeException("Um novo atendimento não deve informar ID.");
        }
        consulta.validarCondicoes();
        if (consulta.getSituacao() == null) {
            consulta.setSituacao(SituacaoConsulta.AGENDADA);
        } else if (consulta.getSituacao() != SituacaoConsulta.AGENDADA) {
            throw new RuntimeException("Um novo atendimento deve começar como AGENDADA.");
        }
        return consultaRepository.save(consulta);
    }

    public ConsultaEspecializada atualizarSituacao(Long id, SituacaoConsulta novaSituacao) {
        ConsultaEspecializada consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));

        consulta.validarSituacao(novaSituacao);

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
