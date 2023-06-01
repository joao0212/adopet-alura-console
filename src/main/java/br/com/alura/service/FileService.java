package br.com.alura.service;

import br.com.alura.dominio.Pet;
import br.com.alura.dominio.TipoPet;

import java.io.*;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileService {

    public void importFile(File file, HttpClient client, PetService petService) {
        System.out.println("----- Serão importados os dados abaixo -----");

        List<Pet> pets = readFile(file);
        pets.forEach(System.out::println);

        for (Pet pet : pets) {
            try {
                petService.createPetAsync(client, pet);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Importação concluída!");
    }

    public void showFile(File file) {
        List<Pet> pets = readFile(file);
        pets.forEach(System.out::println);
    }

    private List<Pet> readFile(File file) {
        List<Pet> pets = new ArrayList<>();
        try {
            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] properties = line.split(";");
                pets.add(new Pet(UUID.fromString(properties[0]), properties[1], TipoPet.valueOf(properties[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pets;
    }
}
