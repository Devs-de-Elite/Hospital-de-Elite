package com.elite.hospital.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.OptBoolean;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo", requireTypeIdForSubtypes = OptBoolean.FALSE)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MedicoClinicoGeral.class, name = "CLINICO_GERAL"),
    @JsonSubTypes.Type(value = MedicoEspecialista.class, name = "ESPECIALISTA"),
    @JsonSubTypes.Type(value = Cirurgiao.class, name = "CIRURGIAO")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String registroProfissional; // CRM
    private String telefone;
    private String email;
    private boolean disponivel = true;

    public abstract void RealizarAtendimento(Atendimento atendimento, String observacoes);

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    protected void validarResponsabilidade(Atendimento atendimento) {
        Profissional responsavel = atendimento.getProfissionalResponsavel();
        if (responsavel != this && (id == null || responsavel == null || !id.equals(responsavel.getId()))) {
            throw new RuntimeException("O profissional não é responsável por este atendimento.");
        }
        atendimento.validarCondicoes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}