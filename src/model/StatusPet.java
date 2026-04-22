package model;

public enum StatusPet {
    DISPONIVEL("Disponível"),
    EM_PROCESSO("Em processo"),
    ADOTADO("Adotado");

    private final String descricao;

    StatusPet(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}