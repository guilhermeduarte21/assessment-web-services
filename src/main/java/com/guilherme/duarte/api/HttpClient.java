package com.guilherme.duarte.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    private static final int TIMEOUT_CONEXAO = 5000;
    private static final int TIMEOUT_LEITURA = 5000;

    public static class Resposta {
        private int statusCode;
        private String body;

        public Resposta(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getBody() {
            return body;
        }

        public boolean sucesso() {
            return statusCode >= 200 && statusCode < 300;
        }
    }

    public Resposta executarGet(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        try {
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setConnectTimeout(TIMEOUT_CONEXAO);
            conexao.setReadTimeout(TIMEOUT_LEITURA);

            int statusCode = conexao.getResponseCode();
            String corpo = lerResposta(conexao);

            return new Resposta(statusCode, corpo);

        } finally {
            conexao.disconnect();
        }
    }

    public Resposta executarPost(String urlString, String jsonCorpo) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        try {
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setConnectTimeout(TIMEOUT_CONEXAO);
            conexao.setReadTimeout(TIMEOUT_LEITURA);

            // Enviar corpo da requisição
            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = jsonCorpo.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = conexao.getResponseCode();
            String corpo = lerResposta(conexao);

            return new Resposta(statusCode, corpo);

        } finally {
            conexao.disconnect();
        }
    }

    private String lerResposta(HttpURLConnection conexao) throws Exception {
        BufferedReader reader;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            // Em caso de erro HTTP, ler error stream
            reader = new BufferedReader(
                    new InputStreamReader(conexao.getErrorStream(), StandardCharsets.UTF_8)
            );
        }

        StringBuilder resposta = new StringBuilder();
        String linha;

        while ((linha = reader.readLine()) != null) {
            resposta.append(linha);
        }

        reader.close();
        return resposta.toString();
    }
}
