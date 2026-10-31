package com.elite.hospital.repository;

import com.elite.hospital.model.ConsultaRotina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface ConsultaRotinaRepository extends JpaRepository<ConsultaRotina, Long>{
    
}