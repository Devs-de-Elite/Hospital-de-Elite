package com.elite.hospital.service;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.model.Cirurgiao;
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

    public MedicoEspecialista cadastrarEspecialista(MedicoEspecialista especialista) {
        if (especialista.getEspecialidade() == null) {
            throw new RuntimeException("O médico especialista deve informar a especialidade.");
        }
        return especialistaRepository.save(especialista);
    }

    public List<MedicoEspecialista> listarEspecialistas(Especialidade especialidade) {
        if (especialidade == null) {
            return especialistaRepository.findAll();
        }
        return especialistaRepository.findByEspecialidade(especialidade);
    }

    public Cirurgiao cadastrarCirurgiao(Cirurgiao cirurgiao) {
        return profissionalRepository.save(cirurgiao);
    }
}
