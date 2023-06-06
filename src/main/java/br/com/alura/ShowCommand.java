package br.com.alura;

import br.com.alura.service.FileService;

import java.io.File;
import java.net.http.HttpClient;

public class ShowCommand implements Command {

    private final File file;

    public ShowCommand(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        FileService fileService = new FileService();

        fileService.showFile(file);
    }
}
