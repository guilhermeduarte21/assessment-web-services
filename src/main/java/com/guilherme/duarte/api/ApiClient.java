package com.guilherme.duarte.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private HttpClient httpClient;
    private Gson gson;

    public ApiClient() {
        this.httpClient = new HttpClient();
        this.gson = new Gson();
    }

    // QUESTÃO 10: GET simples
    public void buscarPostPorId(Long id) {
        try {
            String url = BASE_URL + "/posts/" + id;

            System.out.println("=== QUESTÃO 10: GET SIMPLES ===");
            System.out.println("URL: " + url);
            System.out.println();

            HttpClient.Resposta resposta = httpClient.executarGet(url);

            System.out.println("Status Code: " + resposta.getStatusCode());
            System.out.println("\nBody da Resposta:");
            System.out.println(resposta.getBody());

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // QUESTÃO 11: GET com parâmetros
    public void buscarPostsPorUsuario(Long userId) {
        try {
            String url = BASE_URL + "/posts?userId=" + userId;

            System.out.println("=== QUESTÃO 11: GET COM PARÂMETROS ===");
            System.out.println("URL: " + url);
            System.out.println("Parâmetro userId: " + userId);
            System.out.println();

            HttpClient.Resposta resposta = httpClient.executarGet(url);

            System.out.println("Status Code: " + resposta.getStatusCode());

            // Converter JSON para lista de posts
            Type listType = new TypeToken<List<PostDTO>>(){}.getType();
            List<PostDTO> posts = gson.fromJson(resposta.getBody(), listType);

            System.out.println("Total de posts encontrados: " + posts.size());
            System.out.println("\nLista de títulos dos posts:");
            for (PostDTO post : posts) {
                System.out.println("  - " + post.getTitle());
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // QUESTÃO 12: POST com JSON
    public void criarPost(Long userId, String titulo, String corpo) {
        try {
            String url = BASE_URL + "/posts";

            System.out.println("=== QUESTÃO 12: POST COM JSON ===");
            System.out.println("URL: " + url);
            System.out.println();

            CriarPostRequest request = new CriarPostRequest(userId, titulo, corpo);
            String jsonRequest = gson.toJson(request);

            System.out.println("JSON Enviado:");
            System.out.println(jsonRequest);
            System.out.println();

            HttpClient.Resposta resposta = httpClient.executarPost(url, jsonRequest);

            System.out.println("Status Code: " + resposta.getStatusCode());
            System.out.println("\nResposta da API:");
            System.out.println(resposta.getBody());

            PostDTO postCriado = gson.fromJson(resposta.getBody(), PostDTO.class);
            System.out.println("\nPost criado com ID: " + postCriado.getId());

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApiClient cliente = new ApiClient();

        System.out.println("\n");
        System.out.println("=== CONSUMO DE API REST ===");
        System.out.println("\n");

        // Executar todas as questões
        cliente.buscarPostPorId(1L);
        cliente.buscarPostsPorUsuario(1L);
        cliente.criarPost(
                1L,
                "Post de Teste",
                "post de teste do trabalho da faculdade."
        );
    }
}
