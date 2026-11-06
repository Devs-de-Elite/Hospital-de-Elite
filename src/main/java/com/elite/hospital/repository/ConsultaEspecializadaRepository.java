package com.elite.hospital.repository;

import com.elite.hospital.model.ConsultaEspecializada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaEspecializadaRepository extends JpaRepository<ConsultaEspecializada, Long> {

}
