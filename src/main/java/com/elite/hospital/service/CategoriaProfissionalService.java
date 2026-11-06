package com.elite.hospital.service;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.model.Cirurgiao;
import com.elite.hospital.model.Profissional;
import com.elite.hospital.model.MedicoClinicoGeral;
import com.elite.hospital.repository.PacienteRepository;
import java.util.ArrayList;
import com.elite.hospital.model.MedicoEspecialista;
import com.elite.hospital.repository.MedicoEspecialistaRepository;
import com.elite.hospital.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaProfissionalService {

    @Autowired
    private MedicoEspecialistaRepository especialistaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    public Profissional cadastrar(Profissional profissional) {
        if (profissional.getId() != null) {
            throw new RuntimeException("Um novo profissional não deve informar ID.");
        }
        if (profissional instanceof MedicoEspecialista especialista) {
            return cadastrarEspecialista(especialista);
        }
        return profissionalRepository.save(profissional);
    }

    public MedicoEspecialista encaminhar(Long clinicoId, Long pacienteId, Especialidade especialidade) {
        Profissional profissional = profissionalRepository.findById(clinicoId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!"));
        if (!(profissional instanceof MedicoClinicoGeral clinico)) {
            throw new RuntimeException("O encaminhamento deve ser feito por um clínico geral.");
        }
        return clinico.encaminharPaciente(pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!")),
                new ArrayList<>(listarEspecialistas(especialidade)), especialidade.name());
    }

    public MedicoEspecialista cadastrarEspecialista(MedicoEspecialista especialista) {
        if (especialista.getId() != null) {
            throw new RuntimeException("Um novo profissional não deve informar ID.");
        }
        if (especialista.getEspecialidade() == null) {
            throw new RuntimeException("O médico especialista deve informar a especialidade.");
        }
        return especialistaRepository.save(especialista);
    }

    public List<MedicoEspecialista> listarEspecialistas(Especialidade especialidade) {
        if (especialidade == null) {
            return especialistaRepository.findByDisponivelTrue();
        }
        return especialistaRepository.findByEspecialidadeAndDisponivelTrue(especialidade);
    }

    public Cirurgiao cadastrarCirurgiao(Cirurgiao cirurgiao) {
        return (Cirurgiao) cadastrar(cirurgiao);
    }
}
