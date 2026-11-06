package com.elite.hospital.repository;

import com.elite.hospital.model.Internacao;
import com.elite.hospital.enums.SituacaoInternacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternacaoRepository extends JpaRepository<Internacao, Long> {
    long countByQuartoIdAndSituacao(Long quartoId, SituacaoInternacao situacao);
}
