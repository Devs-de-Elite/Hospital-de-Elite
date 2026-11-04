package com.elite.hospital.repository;

import com.elite.hospital.enums.SituacaoProcedimento;
import com.elite.hospital.model.ProcedimentoCirurgico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedimentoCirurgicoRepository extends JpaRepository<ProcedimentoCirurgico, Long> {

    List<ProcedimentoCirurgico> findByProfissionalResponsavelIdAndSituacaoProcedimento(Long cirurgiaoId, SituacaoProcedimento situacao);
}
