package br.com.alura.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.http.HttpClient;

import static org.mockito.Mockito.*;

public class FileServiceTest {

    private final FileService fileService = new FileService();
    private final PetService petService = mock(PetService.class);
    private final HttpClient httpClient = mock(HttpClient.class);

    @Test
    public void shouldVerifyIfCallPetService() {
        fileService.importFile(new File("lista"), httpClient, petService);

        verify(petService, atLeast(1));
    }

    @Test
    public void shouldVerifyIfNotCallPetService() {
        fileService.showFile(new File("lista"));

        verify(petService, never());
    }
}