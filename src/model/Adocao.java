package model;

import java.time.LocalDate;

public class Adocao {
    private int id;
    private Pet pet;
    private Adotante adotante;
    private StatusAdocao status;
    private LocalDate dataSolicitacao;

    private String nomeCompleto;
    private String idade;
    private String telefone;
    private String endereco;
    private String moraEm;
    private String temOutrosAnimais;
    private String jaTevePets;
    private String motivo;

    public Adocao(int id, Pet pet, Adotante adotante, StatusAdocao status, LocalDate dataSolicitacao,
                  String nomeCompleto, String idade, String telefone, String endereco,
                  String moraEm, String temOutrosAnimais, String jaTevePets, String motivo) {
        this.id = id;
        this.pet = pet;
        this.adotante = adotante;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.nomeCompleto = nomeCompleto;
        this.idade = idade;
        this.telefone = telefone;
        this.endereco = endereco;
        this.moraEm = moraEm;
        this.temOutrosAnimais = temOutrosAnimais;
        this.jaTevePets = jaTevePets;
        this.motivo = motivo;
    }

    public int getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }

    public Adotante getAdotante() {
        return adotante;
    }

    public StatusAdocao getStatus() {
        return status;
    }

    public void setStatus(StatusAdocao status) {
        this.status = status;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getMoraEm() {
        return moraEm;
    }

    public String getTemOutrosAnimais() {
        return temOutrosAnimais;
    }

    public String getJaTevePets() {
        return jaTevePets;
    }

    public String getMotivo() {
        return motivo;
    }
}