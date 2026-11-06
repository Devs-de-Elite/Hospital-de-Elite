package com.elite.hospital.model;

import com.elite.hospital.enums.SituacaoConsulta;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.OptBoolean;
import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo", requireTypeIdForSubtypes = OptBoolean.FALSE)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ConsultaRotina.class, name = "ROTINA"),
    @JsonSubTypes.Type(value = ConsultaEspecializada.class, name = "ESPECIALIZADA"),
    @JsonSubTypes.Type(value = AtendimentoEmergencia.class, name = "EMERGENCIA"),
    @JsonSubTypes.Type(value = ProcedimentoCirurgico.class, name = "CIRURGICO")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Atendimento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private SituacaoConsulta situacao;

    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private Profissional profissionalResponsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public SituacaoConsulta getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoConsulta situacao) {
        validarSituacao(situacao);
        this.situacao = situacao;
    }

    public Profissional getProfissionalResponsavel() { 
        return profissionalResponsavel; 
    }
    public void setProfissionalResponsavel(Profissional profissionalResponsavel) {
        this.profissionalResponsavel = profissionalResponsavel; 
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void validarSituacao(SituacaoConsulta novaSituacao) {
        if (novaSituacao == null) {
            throw new RuntimeException("A situação deve ser informada.");
        }
        if (situacao == null) {
            if (novaSituacao != SituacaoConsulta.AGENDADA) {
                throw new RuntimeException("Um atendimento novo deve começar como AGENDADA.");
            }
            return;
        }
        boolean permitido = false;
            
        if (situacao == SituacaoConsulta.AGENDADA) {
            permitido = novaSituacao == SituacaoConsulta.CONFIRMADA
                    || novaSituacao == SituacaoConsulta.CANCELADA
                    || novaSituacao == SituacaoConsulta.NAO_COMPARECEU;
        
        } else if (situacao == SituacaoConsulta.CONFIRMADA) {
            permitido = novaSituacao == SituacaoConsulta.EM_ATENDIMENTO
                    || novaSituacao == SituacaoConsulta.CANCELADA
                    || novaSituacao == SituacaoConsulta.NAO_COMPARECEU;
        
        } else if (situacao == SituacaoConsulta.EM_ATENDIMENTO) {
            permitido = novaSituacao == SituacaoConsulta.CONCLUIDA;
        }
        
        if (!permitido && situacao != novaSituacao) {
            throw new RuntimeException("Não é possível alterar o atendimento de " + situacao + " para " + novaSituacao + ".");
        }
    }

    public void validarCondicoes() {
        if (paciente == null) {
            throw new RuntimeException("O atendimento deve estar associado a um paciente.");
        }
        if (profissionalResponsavel == null) {
            throw new RuntimeException("O atendimento deve possuir um profissional responsável.");
        }
        if (dataHora == null) {
            throw new RuntimeException("O atendimento deve possuir data e horário.");
        }
    }
}
