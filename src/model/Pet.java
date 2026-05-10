package model;

public class Pet {
    private int id;
    private String nome;
    private String especie;
    private int idade;
    private String porte;
    private StatusPet status;
    private String foto;
    private String personalidade;
    private String historia;
    private String localizacao;
    private Doador doador;
    private String imagemPath;

    public Pet(int id, String nome, String especie, int idade, String porte, StatusPet status,
               String foto, String personalidade, String historia, String localizacao, Doador doador, String imagemPath) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.porte = porte;
        this.status = status;
        this.foto = foto;
        this.personalidade = personalidade;
        this.historia = historia;
        this.localizacao = localizacao;
        this.doador = doador;
        this.imagemPath = imagemPath;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public int getIdade() {
        return idade;
    }

    public String getPorte() {
        return porte;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public StatusPet getStatus() {
        return status;
    }

    public void setStatus(StatusPet status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public String getHistoria() {
        return historia;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public Doador getDoador() {
        return doador;
    }

    public String getImagemPath() {
        return imagemPath;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setImagemPath(String imagemPath) {
        this.imagemPath = imagemPath;
    }
}