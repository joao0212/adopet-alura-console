package br.com.alura;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        File file = new File("lista");
        HttpClient client = configureHttpClient();
        try {
            switch (args[0].trim()) {
                case "import":
                    System.out.println("----- Serão importados os dados abaixo -----");

                    List<Pet> petList = new ArrayList<>();
                    try {
                        InputStream in = new FileInputStream(file);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] properties = line.split(";");
                            Pet pet = new Pet(UUID.fromString(properties[0]), properties[1], TipoPet.valueOf(properties[2]));
                            System.out.println(pet);
                            petList.add(pet);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (Pet pet : petList) {
                        try {
                            createPetAsync(client, pet);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    System.out.println("Importação concluída!");
                    break;
                case "help":
                    System.out.println("Lista de comandos.");
                    if (args.length == 1) {
                        System.out.println("adopet help <parametro> ous simplemente adopet help  " +
                                "comando que exibe informações de ajuda dos comandos.");
                        System.out.println("Adopet (1.0) - Aplicativo de linha de comando (CLI).");
                        System.out.println("Realiza a importação em lote de um arquivos de pets.");
                        System.out.println("Comando possíveis: ");
                        System.out.println(" adopet import <arquivo> comando que realiza a importação do arquivo de pets.");
                        System.out.println(" adopet show   <arquivo> comando que exibe no terminal o conteúdo do arquivo importado.\n\n\n\n");
                        System.out.println("Execute 'adopet.exe help [comando]' para obter mais informações sobre um comando.\n\n\n");
                    } else if (args.length == 2) {
                        if (args[1].equals("import")) {
                            System.out.println(" adopet import <arquivo> " +
                                    "comando que realiza a importação do arquivo de pets.");
                        }
                        if (args[1].equals("show")) {
                            System.out.println(" adopet show <arquivo>  comando que " +
                                    "exibe no terminal o conteúdo do arquivo importado.");
                        }
                    }
                    break;
                case "show":
                    try {
                        InputStream in = new FileInputStream(file);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] properties = line.split(";");
                            Pet pet = new Pet(UUID.fromString(properties[0]), properties[1], TipoPet.valueOf(properties[2]));
                            System.out.println(pet);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "list":
                    List<Pet> pets = listPetsAsync(client);
                    for (Pet pet : pets) {
                        System.out.println(pet);
                    }
                    break;
                default:
                    System.out.println("Comando inválido!");
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Aconteceu uma exceção: " + ex.getMessage());
        }
    }

    private static HttpClient configureHttpClient() {
        HttpClient client = HttpClient.newHttpClient();
        client = client.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        return client;
    }

    private static void createPetAsync(HttpClient client, Pet pet) throws IOException, InterruptedException {
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

    private static List<Pet> listPetsAsync(HttpClient client) throws IOException, InterruptedException {
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
}