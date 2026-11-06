package com.elite.hospital.repository;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.model.MedicoEspecialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoEspecialistaRepository extends JpaRepository<MedicoEspecialista, Long> {

    List<MedicoEspecialista> findByEspecialidadeAndDisponivelTrue(Especialidade especialidade);
    List<MedicoEspecialista> findByDisponivelTrue();
}
