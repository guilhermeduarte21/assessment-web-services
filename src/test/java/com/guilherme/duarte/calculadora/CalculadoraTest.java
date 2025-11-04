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

    @Test
    public void testSubtrair() {
        int a = 10;
        int b = 4;
        int resultadoEsperado = 6;

        int resultado = calculadora.subtrair(a, b);

        assertEquals("10 - 4 deveria resultar em 6", resultadoEsperado, resultado);
    }

    @Test(expected = ArithmeticException.class)
    public void testDividirPorZero() {
        calculadora.dividir(10, 0);
    }

    @Test
    public void testDividirNormal() {
        double resultado = calculadora.dividir(10, 2);
        assertEquals(5.0, resultado, 0.001);
    }
}
