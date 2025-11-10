package com.guilherme.duarte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilherme.duarte.usuario.ValidadorUsuario;
import com.guilherme.duarte.usuario.controller.UsuarioController;
import com.guilherme.duarte.usuario.repository.UsuarioRepositoryMemoria;
import com.guilherme.duarte.usuario.service.UsuarioService;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class App 
{
    private static final int PORTA = 7070;

    public static void main(String[] args) {
        Javalin app = criarApp();
        app.start(PORTA);

        exibirMensagemInicio();
    }

    public static Javalin criarApp() {
        Javalin app = Javalin.create(config -> {
            config.showJavalinBanner = false;

            // Configurar Jackson para serialização JSON
            ObjectMapper objectMapper = new ObjectMapper();
            config.jsonMapper(new JavalinJackson());
        });

        // QUESTÃO 13: Hello World
        app.get("/hello", ctx -> {
            ctx.result("Hello, World!");
        });

        // QUESTÃO 14: CRUD de Usuário
        configurarRotasUsuario(app);

        return app;
    }

    private static void configurarRotasUsuario(Javalin app) {
        UsuarioRepositoryMemoria repository = new UsuarioRepositoryMemoria();
        ValidadorUsuario validador = new ValidadorUsuario();
        UsuarioService service = new UsuarioService(repository, validador);
        UsuarioController controller = new UsuarioController(service);

        controller.registrarRotas(app);
    }

    private static void exibirMensagemInicio() {
        System.out.println();
        System.out.println("URL Base: http://localhost:" + PORTA);
        System.out.println();
        System.out.println("Endpoints disponíveis:");
        System.out.println("  GET    /hello");
        System.out.println("  GET    /usuarios");
        System.out.println("  GET    /usuarios/:id");
        System.out.println("  POST   /usuarios");
        System.out.println("  DELETE /usuarios/:id");
    }
}
