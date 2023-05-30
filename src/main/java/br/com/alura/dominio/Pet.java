package br.com.alura.dominio;

import java.util.UUID;

public class Pet {
    private UUID id;
    private String nome;
    private TipoPet tipo;

    public Pet(UUID id, String nome, TipoPet tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return id.toString() + " - " + nome + " - " + tipo.toString();
    }
}
