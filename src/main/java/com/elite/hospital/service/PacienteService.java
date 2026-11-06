package com.elite.hospital.service;

import com.elite.hospital.model.Paciente;
import com.elite.hospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente cadastrar(Paciente paciente) {
        if (paciente.getId() != null) {
            throw new RuntimeException("Um novo paciente não deve informar ID.");
        }
        return pacienteRepository.save(paciente);
    }
}
