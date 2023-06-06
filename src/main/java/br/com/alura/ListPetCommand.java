package br.com.alura;

import br.com.alura.configuration.HttpClientFactory;
import br.com.alura.service.PetService;

import java.io.IOException;
import java.net.http.HttpClient;

public class ListPetCommand implements Command {

    @Override
    public void execute() {
        try {
            PetService petService = new PetService();
            HttpClient client = new HttpClientFactory().configureHttpClient();
            petService.listPets(client);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
