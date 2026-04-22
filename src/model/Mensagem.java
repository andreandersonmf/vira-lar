package model;

import java.time.LocalDateTime;

public class Mensagem {
    private int id;
    private Usuario remetente;
    private Usuario destinatario;
    private String texto;
    private LocalDateTime data;

    public Mensagem(int id, Usuario remetente, Usuario destinatario, String texto, LocalDateTime data) {
        this.id = id;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.texto = texto;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getData() {
        return data;
    }
}