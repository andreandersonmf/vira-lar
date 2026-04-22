package model;

public enum StatusAdocao {
    EM_ANALISE("Em análise"),
    APROVADA("Aprovada"),
    RECUSADA("Recusada"),
    CONCLUIDA("Concluída");

    private final String descricao;

    StatusAdocao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}