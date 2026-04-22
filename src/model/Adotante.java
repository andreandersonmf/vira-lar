package model;

public class Adotante extends Usuario {
    public Adotante(int id, String nome, String email, String senha, String telefone) {
        super(id, nome, email, senha, telefone);
    }

    @Override
    public String getTipoUsuario() {
        return "Adotante";
    }
}