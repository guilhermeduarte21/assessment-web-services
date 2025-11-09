package com.guilherme.duarte.usuario;

public class ValidadorUsuario {

    public boolean validarEmail(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return false;
        }
        return usuario.getEmail().contains("@") && usuario.getEmail().contains(".");
    }

    public boolean validarNome(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            return false;
        }
        return usuario.getNome().length() >= 3;
    }
}
