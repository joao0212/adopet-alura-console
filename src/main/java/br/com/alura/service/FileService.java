package br.com.alura.service;

import br.com.alura.Pet;
import br.com.alura.TipoPet;

import java.io.*;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileService {

    public void importFile(File file, HttpClient client, PetService petService) {
        List<Pet> petList = new ArrayList<>();
        try {
            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] properties = line.split(";");
                Pet pet = new Pet(UUID.fromString(properties[0]), properties[1], TipoPet.Cachorro);
                System.out.println(pet);
                petList.add(pet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Pet pet : petList) {
            try {
                petService.createPetAsync(client, pet);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Importação concluída!");
    }

    public void showFile(File file) {
        try {
            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            System.out.println("----- Serão importados os dados abaixo -----");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] properties = line.split(";");
                Pet pet = new Pet(UUID.fromString(properties[0]), properties[1], TipoPet.Cachorro);
                System.out.println(pet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
