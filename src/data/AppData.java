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
                "joao@viralar.com",
                "123",
                "(11) 99999-9999"
        );

        Adotante adotante = new Adotante(
                nextUsuarioId(),
                "Maria Souza",
                "maria@viralar.com",
                "123",
                "(11) 98888-8888"
        );

        usuarios.add(doador);
        usuarios.add(adotante);

        pets.add(new Pet(
                nextPetId(),
                "Rex",
                "Cachorro",
                2,
                "Médio",
                StatusPet.DISPONIVEL,
                "",
                "Brincalhão",
                "Rex foi resgatado das ruas e hoje está pronto para ganhar uma família cheia de amor.",
                "São Paulo - SP",
                doador
        ));

        pets.add(new Pet(
                nextPetId(),
                "Mimi",
                "Cachorro",
                3,
                "Pequeno",
                StatusPet.DISPONIVEL,
                "",
                "Carinhosa",
                "Mimi é muito dócil e gosta de brincar com crianças.",
                "Guarulhos - SP",
                doador
        ));

        pets.add(new Pet(
                nextPetId(),
                "Bolinha",
                "Cachorro",
                1,
                "Médio",
                StatusPet.EM_PROCESSO,
                "",
                "Alegre",
                "Bolinha adora passear e se dá bem com outros animais.",
                "Osasco - SP",
                doador
        ));

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
                doador
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