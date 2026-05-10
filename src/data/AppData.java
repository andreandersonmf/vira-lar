package data;

import java.util.ArrayList;
import java.util.List;

import model.Adocao;
import model.Adotante;
import model.Doador;
import model.Favorito;
import model.Mensagem;
import model.Pet;
import model.StatusPet;
import model.Usuario;

public class AppData {
    public static final List<Usuario> usuarios = new ArrayList<>();
    public static final List<Pet> pets = new ArrayList<>();
    public static final List<Adocao> adocoes = new ArrayList<>();
    public static final List<Mensagem> mensagens = new ArrayList<>();
    public static final List<Favorito> favoritos = new ArrayList<>();

    public static Usuario usuarioLogado;

    private static int usuarioId = 1;
    private static int petId = 1;
    private static int adocaoId = 1;
    private static int mensagemId = 1;
    private static int favoritoId = 1;

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }

        Doador doador = new Doador(
                nextUsuarioId(),
                "João Silva",
                "joao.andrade1@gmail.com",
                "1234",
                "(11) 99999-9999"
        );

        Adotante adotante = new Adotante(
                nextUsuarioId(),
                "Maria Souza",
                "mariasilvana@outlook.com",
                "1234",
                "(11) 98888-8888"
        );

        usuarios.add(doador);
        usuarios.add(adotante);

        pets.add(new Pet(
                nextPetId(),
                "Luna",
                "Gato",
                1,
                "Pequeno",
                StatusPet.DISPONIVEL,
                "",
                "Calma",
                "Luna é tranquila, observadora e ótima companhia para apartamento.",
                "Santo André - SP",
                doador,
                "/resources/luna.jpg"
        ));

        initialized = true;
    }

    public static int nextUsuarioId() {
        return usuarioId++;
    }

    public static int nextPetId() {
        return petId++;
    }

    public static int nextAdocaoId() {
        return adocaoId++;
    }

    public static int nextMensagemId() {
        return mensagemId++;
    }

    public static int nextFavoritoId() {
        return favoritoId++;
    }
}