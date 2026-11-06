package com.elite.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.elite.hospital.enums.SituacaoInternacao;

@Entity
public class Internacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Profissional profissionalResponsavel;
    @ManyToOne
    private Quarto quarto;
    private LocalDate dataEntrada;
    private LocalDate dataPrevistaAlta;
    private LocalDate dataAlta;
    private String observacoes;
    @Enumerated(EnumType.STRING)
    private SituacaoInternacao situacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissionalResponsavel() {
        return profissionalResponsavel;
    }

    public void setProfissionalResponsavel(Profissional profissionalResponsavel) {
        this.profissionalResponsavel = profissionalResponsavel;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataPrevistaAlta() {
        return dataPrevistaAlta;
    }

    public void setDataPrevistaAlta(LocalDate dataPrevistaAlta) {
        this.dataPrevistaAlta = dataPrevistaAlta;
    }

    public LocalDate getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(LocalDate dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public SituacaoInternacao getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoInternacao situacao) {
        this.situacao = situacao;
    }

    public void validarCondicoes() {
        if (paciente == null || profissionalResponsavel == null || quarto == null || dataEntrada == null) {
            throw new RuntimeException("Informe paciente, profissional, quarto e data de entrada.");
        }
        if (dataPrevistaAlta != null && dataPrevistaAlta.isBefore(dataEntrada)) {
            throw new RuntimeException("A previsão de alta não pode anteceder a entrada.");
        }
    }

    public void registrarAlta(LocalDate data) {
        if (situacao != SituacaoInternacao.ATIVA || data == null || data.isBefore(dataEntrada)) {
            throw new RuntimeException("A alta exige internação ativa e data igual ou posterior à entrada.");
        }
        dataAlta = data;
        situacao = SituacaoInternacao.ALTA;
    }
}
