package com.elite.hospital.model;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.enums.SituacaoConsulta;
import jakarta.persistence.*;

@Entity
public class ConsultaEspecializada extends Atendimento {

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private String observacoes;

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public void validarCondicoes() {
        super.validarCondicoes();
        if (especialidade == null) {
            throw new RuntimeException("A consulta especializada deve informar a especialidade.");
        }
        if (!(getProfissionalResponsavel() instanceof MedicoEspecialista especialista)) {
            throw new RuntimeException("A consulta especializada deve ser realizada por um médico especialista.");
        }
        if (especialista.getEspecialidade() != especialidade) {
            throw new RuntimeException("A especialidade do profissional não é compatível com o atendimento solicitado.");
        }
    }

}