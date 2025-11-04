package com.guilherme.duarte.calculadora;

public class Calculadora {
    public int somar(int a, int b) {
        return a + b;
    }

    public int subtrair(int a, int b) {
        return a - b;
    }

    public double dividir(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Não é possível dividir por zero");
        }
        return (double) a / b;
    }
}
