package br.com.alura.service;

import br.com.alura.dominio.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class PetService {

    public void listPets(HttpClient client) throws IOException, InterruptedException {
        List<Pet> pets = listPetsAsync(client);
        for (Pet pet : pets) {
            System.out.println(pet);
        }
    }

    private List<Pet> listPetsAsync(HttpClient client) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/pets"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error listing pets: " + response.body());
        }
        Pet[] pets = objectMapper.readValue(response.body(), Pet[].class);
        return Arrays.stream(pets).toList();
    }

    public void createPetAsync(HttpClient client, Pet pet) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/pets"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(pet)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error creating pet: " + response.body());
        }
    }
}
