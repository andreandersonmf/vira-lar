package service;

import java.util.ArrayList;
import java.util.List;

import data.AppData;
import model.Doador;
import model.Pet;
import model.StatusPet;

public class PetService {

    public List<Pet> listarTodos() {
        return new ArrayList<>(AppData.pets);
    }

    public List<Pet> listarDisponiveis() {
        List<Pet> lista = new ArrayList<>();
        for (Pet pet : AppData.pets) {
            if (pet.getStatus() == StatusPet.DISPONIVEL) {
                lista.add(pet);
            }
        }
        return lista;
    }

    public List<Pet> buscar(String especie, String faixaIdade, String localizacao) {
        List<Pet> resultado = new ArrayList<>();

        for (Pet pet : AppData.pets) {
            boolean especieOk = especie == null || especie.equals("Todos") || pet.getEspecie().equalsIgnoreCase(especie);
            boolean localizacaoOk = localizacao == null || localizacao.equals("Todas") || pet.getLocalizacao().toLowerCase().contains(localizacao.toLowerCase());

            boolean idadeOk = true;
            if (faixaIdade != null && !faixaIdade.equals("Todas")) {
                switch (faixaIdade) {
                    case "Até 1 ano" -> idadeOk = pet.getIdade() <= 1;
                    case "2 a 4 anos" -> idadeOk = pet.getIdade() >= 2 && pet.getIdade() <= 4;
                    case "5+ anos" -> idadeOk = pet.getIdade() >= 5;
                }
            }

            if (especieOk && localizacaoOk && idadeOk) {
                resultado.add(pet);
            }
        }

        return resultado;
    }

    public Pet cadastrarPet(String nome, String especie, int idade, String porte,
                            String personalidade, String historia, String localizacao, Doador doador) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do pet.");
        }
        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("Informe a espécie do pet.");
        }
        if (porte == null || porte.isBlank()) {
            throw new IllegalArgumentException("Informe o porte do pet.");
        }
        if (localizacao == null || localizacao.isBlank()) {
            throw new IllegalArgumentException("Informe a localização.");
        }

        Pet pet = new Pet(
                AppData.nextPetId(),
                nome,
                especie,
                idade,
                porte,
                StatusPet.DISPONIVEL,
                "",
                personalidade,
                historia,
                localizacao,
                doador
        );

        AppData.pets.add(pet);
        return pet;
    }

    public List<Pet> petsDoDoador(Doador doador) {
        List<Pet> lista = new ArrayList<>();
        for (Pet pet : AppData.pets) {
            if (pet.getDoador().getId() == doador.getId()) {
                lista.add(pet);
            }
        }
        return lista;
    }
}