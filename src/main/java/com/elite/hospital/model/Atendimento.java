package com.elite.hospital.model;

import com.elite.hospital.enums.SituacaoConsulta;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
