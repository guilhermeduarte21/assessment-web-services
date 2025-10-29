package com.guilherme.duarte.calculadora;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Before
    public void setUp() throws Exception {
        calculadora = new Calculadora();
    }

    @Test
    public void testSomar() {
        int a = 5;
        int b = 10;
        int resultadoEsperado = 15;

        int resultado = calculadora.somar(a, b);

        assertEquals(resultadoEsperado, resultado);
    }
}
