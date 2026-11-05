public enum SituacaoConsulta {
    AGENDADO("Agendada"),
    CONFIRMADA("Confirmada"),
    EM_ATENDIMENTO("Em Atendimento"),
    CONCLUIDA("Concluida"),
    CANCELADA("Cancelada"),
    NAO_COMPARECEU("Nao Compareceu");

    private String status;

    private SituacaoConsulta(String status){
        this.status = status;
    }

    public boolean podeConcluir(){
        return this != CONCLUIDA && this != CANCELADA;
    }
}
