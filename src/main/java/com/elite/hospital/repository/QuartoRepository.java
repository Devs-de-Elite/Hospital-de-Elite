package com.elite.hospital.repository;

import com.elite.hospital.model.Quarto;
import com.elite.hospital.enums.SituacaoInternacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

}
