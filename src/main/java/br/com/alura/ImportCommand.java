package br.com.alura;

import br.com.alura.configuration.HttpClientFactory;
import br.com.alura.service.FileService;
import br.com.alura.service.PetService;

import java.io.File;
import java.net.http.HttpClient;

public class ImportCommand implements Command {

    private final File file;

    public ImportCommand(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        HttpClient client = new HttpClientFactory().configureHttpClient();
        FileService fileService = new FileService();
        PetService petService = new PetService();

        fileService.importFile(file, client, petService);
    }
}
