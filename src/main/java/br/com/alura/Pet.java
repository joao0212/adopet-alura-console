package br.com.alura;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Pet {
    private final UUID id;
    private final String nome;
    private final TipoPet tipoPet;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Pet(@JsonProperty("id") UUID id, @JsonProperty("nome") String nome, @JsonProperty("tipoPet") TipoPet tipoPet) {
        this.id = id;
        this.nome = nome;
        this.tipoPet = tipoPet;
    }

    @Override
    public String toString() {
        return id.toString() + " - " + nome + " - " + tipoPet.toString();
    }
}
