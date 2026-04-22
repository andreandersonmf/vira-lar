package model;

public class Favorito {
    private int id;
    private Usuario usuario;
    private Pet pet;

    public Favorito(int id, Usuario usuario, Pet pet) {
        this.id = id;
        this.usuario = usuario;
        this.pet = pet;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Pet getPet() {
        return pet;
    }
}