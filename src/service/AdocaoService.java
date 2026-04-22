package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.AppData;
import model.Adocao;
import model.Adotante;
import model.Pet;
import model.StatusAdocao;
import model.StatusPet;

public class AdocaoService {

    public Adocao solicitarAdocao(Pet pet, Adotante adotante, String nomeCompleto, String idade,
                                  String telefone, String endereco, String moraEm,
                                  String temOutrosAnimais, String jaTevePets, String motivo) {
        if (pet == null) {
            throw new IllegalArgumentException("Selecione um pet.");
        }
        if (adotante == null) {
            throw new IllegalArgumentException("Faça login como adotante para solicitar adoção.");
        }
        if (pet.getStatus() == StatusPet.ADOTADO) {
            throw new IllegalArgumentException("Este pet já foi adotado.");
        }

        Adocao adocao = new Adocao(
                AppData.nextAdocaoId(),
                pet,
                adotante,
                StatusAdocao.EM_ANALISE,
                LocalDate.now(),
                nomeCompleto,
                idade,
                telefone,
                endereco,
                moraEm,
                temOutrosAnimais,
                jaTevePets,
                motivo
        );

        AppData.adocoes.add(adocao);
        pet.setStatus(StatusPet.EM_PROCESSO);
        return adocao;
    }

    public void aprovarAdocao(Adocao adocao) {
        adocao.setStatus(StatusAdocao.APROVADA);
        adocao.getPet().setStatus(StatusPet.ADOTADO);
    }

    public void recusarAdocao(Adocao adocao) {
        adocao.setStatus(StatusAdocao.RECUSADA);
        adocao.getPet().setStatus(StatusPet.DISPONIVEL);
    }

    public List<Adocao> listarDoAdotante(Adotante adotante) {
        List<Adocao> lista = new ArrayList<>();
        for (Adocao adocao : AppData.adocoes) {
            if (adocao.getAdotante().getId() == adotante.getId()) {
                lista.add(adocao);
            }
        }
        return lista;
    }
}