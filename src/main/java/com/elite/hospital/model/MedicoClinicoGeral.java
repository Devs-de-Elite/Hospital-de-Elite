package com.elite.hospital.model;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public class MedicoClinicoGeral extends Profissional{
    private ArrayList<ConsultaRotina> listaConsultas;



    @Override
    public void RealizarAtendimento(ConsultaRotina consulta, String observacoes){
        if (consulta.situacao == SituacaoConsulta.CONFIRMADA) {
            consulta.observacoes = observacoes;
            listaConsultas.add(consulta);
            consulta.situacao = SituacaoConsulta.CONCLUIDA;
        } else {
            System.out.println("Essa consulta já foi ou não pode ser realizada");
        }
    }

    public void encaminharPaciente(Paciente paciente, ArrayList<MedicoEspecialista> listaMedicos, String especializacao){
        for (MedicoEspecialista medico : listaMedicos) {
            if (especializacao.equals(medico.getEspecializacao())) {
                if (medico.disponivel) {
                    System.out.println("Paciente encaminhado para o especialista: " + medico.getNome());
                }
            }
        }
    }
}
