package br.com.alura;

import br.com.alura.configuration.HttpClientFactory;
import br.com.alura.service.FileService;
import br.com.alura.service.PetService;

import java.io.*;
import java.net.http.HttpClient;

public class Main {

    public static void main(String[] args) {
        File file = new File("lista");
        HttpClient client = new HttpClientFactory().configureHttpClient("http://localhost:8081");
        FileService fileService = new FileService();
        PetService petService = new PetService();
        String option = args[0].trim();
        try {
            switch (option) {
                case "import": fileService.importFile(file, client, petService);
                    break;
                case "help": callHelpOption(args);
                    break;
                case "show": fileService.showFile(file);
                    break;
                case "list": petService.listPets(client);
                    break;
                default:
                    System.out.println("Comando inválido!");
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Aconteceu uma exceção: " + ex.getMessage());
        }
    }

    private static void callHelpOption(String[] args) {
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
    }
}