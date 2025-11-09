package com.guilherme.duarte.usuario;

public class GeradorDeUsuarios {

    private static long contadorId = 1;

    public static Usuario gerarUsuario() {
        long id = contadorId++;
        return new Usuario(
                id,
                "Usuario " + id,
                "usuario" + id + "@email.com"
        );
    }

    public static Usuario gerarUsuario(String nome) {
        long id = contadorId++;
        String emailGerado = nome.toLowerCase().replace(" ", ".") + "@email.com";
        return new Usuario(id, nome, emailGerado);
    }

    public static Usuario gerarUsuarioComEmailInvalido() {
        Usuario usuario = gerarUsuario();
        usuario.setEmail("email-sem-arroba");
        return usuario;
    }

    public static Usuario gerarUsuarioComNomeCurto() {
        Usuario usuario = gerarUsuario();
        usuario.setNome("Ab");
        return usuario;
    }

    public static void resetarContador() {
        contadorId = 1;
    }
}
