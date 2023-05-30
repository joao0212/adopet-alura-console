package br.com.alura.configuration;

import java.net.http.HttpClient;

public class HttpClientFactory {

    public HttpClient configureHttpClient(String url) {
        HttpClient client = HttpClient.newHttpClient();
        client = client.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        return client;
    }
}
