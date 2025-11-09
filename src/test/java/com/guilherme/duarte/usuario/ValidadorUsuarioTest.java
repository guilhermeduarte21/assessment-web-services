package com.guilherme.duarte.usuario;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidadorUsuarioTest {

    private ValidadorUsuario validador;

    @Before
    public void setUp() {
        validador = new ValidadorUsuario();
        GeradorDeUsuarios.resetarContador();
    }

    @Test
    public void testValidarEmailComUsuarioPadrao() {
        Usuario usuario = GeradorDeUsuarios.gerarUsuario();
        assertTrue(validador.validarEmail(usuario));
    }

    @Test
    public void testValidarEmailInvalido() {
        Usuario usuario = GeradorDeUsuarios.gerarUsuarioComEmailInvalido();
        assertFalse(validador.validarEmail(usuario));
    }

    @Test
    public void testValidarNomeComUsuarioPadrao() {
        Usuario usuario = GeradorDeUsuarios.gerarUsuario();
        assertTrue(validador.validarNome(usuario));
    }

    @Test
    public void testValidarNomeCurto() {
        Usuario usuario = GeradorDeUsuarios.gerarUsuarioComNomeCurto();
        assertFalse(validador.validarNome(usuario));
    }

}
