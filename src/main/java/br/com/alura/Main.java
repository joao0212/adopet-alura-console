package br.com.alura;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        File file = new File("lista");
        String option = args[0].trim();

        CommandExecutor commandExecutor = new CommandExecutor();

        try {
            switch (option) {
                case "help" -> commandExecutor.executeCommand(new HelpCommand());
                case "import" -> commandExecutor.executeCommand(new ImportCommand(file));
                case "show" -> commandExecutor.executeCommand(new ShowCommand(file));
                case "list" -> commandExecutor.executeCommand(new ListPetCommand());
                default -> System.out.println("Comando inválido!");
            }
        } catch (Exception ex) {
            System.out.println("Aconteceu uma exceção: " + ex.getMessage());
        }
    }
}