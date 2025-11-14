package com.guilherme.duarte.usuario.controller;

import com.google.gson.Gson;
import com.guilherme.duarte.App;
import com.guilherme.duarte.usuario.Usuario;
import io.javalin.Javalin;
import okhttp3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UsuarioControllerTest {

    private static final String BASE_URL = "http://localhost:7001";
    private static final MediaType JSON = MediaType.parse("application/json");

    private Javalin app;
    private OkHttpClient client;
    private Gson gson;

    @Before
    public void setUp() {
        // Iniciar aplicação em porta diferente para testes
        app = App.criarApp();
        app.start(7001);

        client = new OkHttpClient();
        gson = new Gson();

        // Aguardar servidor iniciar
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        app.stop();
    }

    // ===== TESTE HAPPY PATH =====

    @Test
    public void testCriarUsuarioComDadosValidos() throws IOException {
        // Arrange
        Usuario usuario = new Usuario(null, "Maria Silva", "maria@example.com");
        String json = gson.toJson(usuario);

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "/usuarios")
                .post(body)
                .build();

        // Act
        Response response = client.newCall(request).execute();

        // Assert
        assertEquals("Status deve ser 201 Created", 201, response.code());

        String responseBody = response.body().string();
        Usuario usuarioCriado = gson.fromJson(responseBody, Usuario.class);

        assertNotNull("ID deve ser gerado automaticamente", usuarioCriado.getId());
        assertEquals("Maria Silva", usuarioCriado.getNome());
        assertEquals("maria@example.com", usuarioCriado.getEmail());

        System.out.println("✓ Teste Happy Path passou - Usuário criado com sucesso");
    }

    // ===== TESTE CORNER CASE =====

    @Test
    public void testBuscarUsuarioInexistente() throws IOException {
        // Arrange
        Request request = new Request.Builder()
                .url(BASE_URL + "/usuarios/999")
                .get()
                .build();

        // Act
        Response response = client.newCall(request).execute();

        // Assert
        assertEquals("Status deve ser 404 Not Found", 404, response.code());

        String responseBody = response.body().string();
        assertTrue("Mensagem deve indicar usuário não encontrado",
                responseBody.contains("não encontrado"));

        System.out.println("✓ Teste Corner Case passou - Retornou 404 corretamente");
    }

    // ===== TESTES ADICIONAIS =====

    @Test
    public void testListarUsuariosVazio() throws IOException {
        // Arrange
        Request request = new Request.Builder()
                .url(BASE_URL + "/usuarios")
                .get()
                .build();

        // Act
        Response response = client.newCall(request).execute();

        // Assert
        assertEquals(200, response.code());
        String responseBody = response.body().string();
        assertEquals("[]", responseBody);
    }

    @Test
    public void testCriarUsuarioComEmailInvalido() throws IOException {
        // Arrange
        Usuario usuario = new Usuario(null, "Pedro Santos", "email-invalido");
        String json = gson.toJson(usuario);

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "/usuarios")
                .post(body)
                .build();

        // Act
        Response response = client.newCall(request).execute();

        // Assert
        assertEquals("Status deve ser 400 Bad Request", 400, response.code());

        String responseBody = response.body().string();
        assertTrue("Mensagem deve indicar email inválido",
                responseBody.toLowerCase().contains("email"));
    }

    @Test
    public void testCriarUsuarioComNomeCurto() throws IOException {
        // Arrange
        Usuario usuario = new Usuario(null, "AB", "ab@example.com");
        String json = gson.toJson(usuario);

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "/usuarios")
                .post(body)
                .build();

        // Act
        Response response = client.newCall(request).execute();

        // Assert
        assertEquals("Status deve ser 400 Bad Request", 400, response.code());

        String responseBody = response.body().string();
        assertTrue("Mensagem deve indicar nome inválido",
                responseBody.toLowerCase().contains("nome"));
    }

    @Test
    public void testDeletarUsuarioInexistente() throws IOException {
        // Arrange
        Request request = new Request.Builder()
                .url(BASE_URL + "/usuarios/999")
                .delete()
                .build();

        // Act
        Response response = client.newCall(request).execute();

        // Assert
        assertEquals("Status deve ser 404 Not Found", 404, response.code());
    }

    @Test
    public void testFluxoCompletoDeUsuario() throws IOException {
        // 1. Criar usuário
        Usuario novoUsuario = new Usuario(null, "Ana Costa", "ana@example.com");
        String jsonCriar = gson.toJson(novoUsuario);

        RequestBody bodyCriar = RequestBody.create(jsonCriar, JSON);
        Request requestCriar = new Request.Builder()
                .url(BASE_URL + "/usuarios")
                .post(bodyCriar)
                .build();

        Response responseCriar = client.newCall(requestCriar).execute();
        assertEquals(201, responseCriar.code());

        Usuario criado = gson.fromJson(responseCriar.body().string(), Usuario.class);
        Long id = criado.getId();
        assertNotNull("ID deve ser gerado", id);

        // 2. Buscar usuário criado
        Request requestBuscar = new Request.Builder()
                .url(BASE_URL + "/usuarios/" + id)
                .get()
                .build();

        Response responseBuscar = client.newCall(requestBuscar).execute();
        assertEquals(200, responseBuscar.code());

        Usuario encontrado = gson.fromJson(responseBuscar.body().string(), Usuario.class);
        assertEquals("Ana Costa", encontrado.getNome());

        // 3. Deletar usuário
        Request requestDeletar = new Request.Builder()
                .url(BASE_URL + "/usuarios/" + id)
                .delete()
                .build();

        Response responseDeletar = client.newCall(requestDeletar).execute();
        assertEquals(204, responseDeletar.code());

        // 4. Verificar que não existe mais
        Request requestVerificar = new Request.Builder()
                .url(BASE_URL + "/usuarios/" + id)
                .get()
                .build();

        Response responseVerificar = client.newCall(requestVerificar).execute();
        assertEquals(404, responseVerificar.code());

        System.out.println("✓ Teste de fluxo completo passou - CRUD funcionando");
    }

    @Test
    public void testBuscarComIdInvalido() throws IOException {
        // Arrange
        Request request = new Request.Builder()
                .url(BASE_URL + "/usuarios/abc")
                .get()
                .build();

        // Act
        Response response = client.newCall(request).execute();

        // Assert
        assertEquals("Status deve ser 400 Bad Request", 400, response.code());
    }
}