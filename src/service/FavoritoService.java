package service;

import java.util.ArrayList;
import java.util.List;

import data.AppData;
import model.Adotante;
import model.Favorito;
import model.Pet;

public class FavoritoService {

    public void adicionarFavorito(Adotante adotante, Pet pet) {
        if (adotante == null) {
            throw new IllegalArgumentException("Faça login como adotante para favoritar pets.");
        }
        if (pet == null) {
            throw new IllegalArgumentException("Selecione um pet para favoritar.");
        }
        if (ehFavorito(adotante, pet)) {
            return;
        }

        AppData.favoritos.add(new Favorito(AppData.nextFavoritoId(), adotante, pet));
    }

    public void removerFavorito(Adotante adotante, Pet pet) {
        if (adotante == null || pet == null) {
            return;
        }

        AppData.favoritos.removeIf(favorito ->
                favorito.getUsuario().getId() == adotante.getId()
                        && favorito.getPet().getId() == pet.getId()
        );
    }

    public boolean ehFavorito(Adotante adotante, Pet pet) {
        if (adotante == null || pet == null) {
            return false;
        }

        for (Favorito favorito : AppData.favoritos) {
            if (favorito.getUsuario().getId() == adotante.getId()
                    && favorito.getPet().getId() == pet.getId()) {
                return true;
            }
        }
        return false;
    }

    public void alternarFavorito(Adotante adotante, Pet pet) {
        if (ehFavorito(adotante, pet)) {
            removerFavorito(adotante, pet);
        } else {
            adicionarFavorito(adotante, pet);
        }
    }

    public List<Pet> listarPetsFavoritos(Adotante adotante) {
        List<Pet> lista = new ArrayList<>();
        if (adotante == null) {
            return lista;
        }

        for (Favorito favorito : AppData.favoritos) {
            if (favorito.getUsuario().getId() == adotante.getId()) {
                lista.add(favorito.getPet());
            }
        }
        return lista;
    }
}
