package com.guilherme.duarte;

import io.javalin.Javalin;

public class App 
{
    private static final int PORTA = 7070;

    public static void main(String[] args) {
        Javalin app = Javalin.create(/*config*/)
                .get("/hello", ctx -> ctx.result("Hello, World!"))
                .start(PORTA);
    }

    public static Javalin criarApp() {
        Javalin app = Javalin.create(config -> {
            config.showJavalinBanner = false;
        });

        // QUESTÃO 13: Endpoint Hello World
        app.get("/hello", ctx -> {
            ctx.result("Hello, World!");
        });

        return app;
    }
}
