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
        if (pet.getStatus() != StatusPet.DISPONIVEL) {
            continue;
        }
        
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
                            String personalidade, String historia, String localizacao,
                            Doador doador, String imagemPath) {
        validarDadosPet(nome, especie, idade, porte, localizacao, doador);

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
                doador,
                imagemPath
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
    public Pet editarPet(Pet pet, Doador doador, String nome, String especie, int idade, String porte,
                         String personalidade, String historia, String localizacao, String imagemPath) {
        validarPermissaoDoador(pet, doador);
        validarDadosPet(nome, especie, idade, porte, localizacao, doador);

        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setIdade(idade);
        pet.setPorte(porte);
        pet.setPersonalidade(personalidade);
        pet.setHistoria(historia);
        pet.setLocalizacao(localizacao);
        pet.setImagemPath(imagemPath);

        return pet;
    }

    public void excluirPet(Pet pet, Doador doador) {
        validarPermissaoDoador(pet, doador);

        AppData.adocoes.removeIf(adocao -> adocao.getPet().getId() == pet.getId());
        AppData.favoritos.removeIf(favorito -> favorito.getPet().getId() == pet.getId());
        AppData.pets.removeIf(p -> p.getId() == pet.getId());
    }

    private void validarPermissaoDoador(Pet pet, Doador doador) {
        if (pet == null) {
            throw new IllegalArgumentException("Selecione um pet.");
        }
        if (doador == null) {
            throw new IllegalArgumentException("Apenas usuários do tipo Doador podem alterar pets.");
        }
        if (pet.getDoador() == null || pet.getDoador().getId() != doador.getId()) {
            throw new IllegalArgumentException("Você só pode alterar pets cadastrados por você.");
        }
    }

    private void validarDadosPet(String nome, String especie, int idade, String porte, String localizacao, Doador doador) {
        if (doador == null) {
            throw new IllegalArgumentException("Apenas usuários do tipo Doador podem cadastrar ou alterar pets.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Informe o nome do pet.");
        }
        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("Informe a espécie do pet.");
        }
        if (idade < 0) {
            throw new IllegalArgumentException("A idade não pode ser negativa.");
        }
        if (porte == null || porte.isBlank()) {
            throw new IllegalArgumentException("Informe o porte do pet.");
        }
        if (localizacao == null || localizacao.isBlank()) {
            throw new IllegalArgumentException("Informe a localização.");
        }
    }

}