package com.guilherme.duarte.usuario.controller;

import com.guilherme.duarte.usuario.Usuario;
import com.guilherme.duarte.usuario.service.UsuarioService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UsuarioController {
    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public void registrarRotas(Javalin app) {
        app.get("/usuarios", this::listarTodos);
        app.get("/usuarios/{id}", this::buscarPorId);
        app.post("/usuarios", this::criar);
        app.delete("/usuarios/{id}", this::deletar);
    }

    private void listarTodos(Context ctx) {
        try {
            ctx.json(service.listarTodos());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao listar usuários: " + e.getMessage());
        }
    }

    private void buscarPorId(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Usuario usuario = service.buscarPorId(id);
            ctx.json(usuario);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(404).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    private void criar(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario criado = service.criar(usuario);
            ctx.status(201).json(criado);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao criar usuário: " + e.getMessage());
        }
    }

    private void deletar(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            service.deletar(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(404).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
