package com.elite.hospital.repository;

import com.elite.hospital.model.Atendimento;
import com.elite.hospital.enums.SituacaoConsulta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findBySituacaoIn(List<SituacaoConsulta> situacoes);
    List<Atendimento> findBySituacao(SituacaoConsulta situacao);
}
