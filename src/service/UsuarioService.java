package service;

import data.AppData;
import model.Adotante;
import model.Doador;
import model.Usuario;

public class UsuarioService {

    public Usuario login(String email, String senha) {
        for (Usuario usuario : AppData.usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email) && usuario.getSenha().equals(senha)) {
                AppData.usuarioLogado = usuario;
                return usuario;
            }
        }
        return null;
    }

    public void logout() {
        AppData.usuarioLogado = null;
    }

    public Usuario getUsuarioLogado() {
        return AppData.usuarioLogado;
    }

    public Usuario cadastrarUsuario(String nome, String email, String senha, String telefone, String tipo) {
        validarCadastro(nome, email, senha);

        for (Usuario usuario : AppData.usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
            }
        }

        Usuario novoUsuario;
        if ("Doador".equalsIgnoreCase(tipo)) {
            novoUsuario = new Doador(AppData.nextUsuarioId(), nome, email, senha, telefone);
        } else {
            novoUsuario = new Adotante(AppData.nextUsuarioId(), nome, email, senha, telefone);
        }

        AppData.usuarios.add(novoUsuario);
        AppData.usuarioLogado = novoUsuario;
        return novoUsuario;
    }

    private void validarCadastro(String nome, String email, String senha) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Informe o nome.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Informe o e-mail.");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Informe a senha.");
        }
    }
}