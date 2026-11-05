package com.elite.hospital.model;

public class MedicoEspecialista extends Profissional{
    private String especilizacao;

    public void RealizarConsultaEspecializada(ConsultaEspecializada consulta, String observacao, String area){
        if (area.equals(especilizacao)){
            if (consulta.situacao == SituacaoConsulta.CONFIRMADA) {
                consulta.observacoes = observacoes;
                consulta.situacao = SituacaoConsulta.CONCLUIDA;
            } else {
                System.out.println("Essa consulta já foi ou não pode ser realizada");
            }
        } else {
            System.out.print("Esse profissonal não realiza consultas dessa área médica.");
        }
    }
}
