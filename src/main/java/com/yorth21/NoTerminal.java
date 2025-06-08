package com.yorth21;

public class NoTerminal {
    private double valorNumerico;     // Resultado numérico si aplica
    private boolean valorLogico;      // Resultado lógico si aplica
    private boolean esLogico;         // ¿El valor representa true/false?
    private boolean esRelacional;     // ¿Proviene de una comparación?

    public NoTerminal() {}

    // Constructores convenientes
    public static NoTerminal conValorNumerico(double valor) {
        NoTerminal nt = new NoTerminal();
        nt.valorNumerico = valor;
        nt.esLogico = false;
        nt.esRelacional = false;
        return nt;
    }

    public static NoTerminal conValorLogico(boolean valor, boolean relacional) {
        NoTerminal nt = new NoTerminal();
        nt.valorLogico = valor;
        nt.esLogico = true;
        nt.esRelacional = relacional;
        return nt;
    }

    // Getters
    public double getValorNumerico() {
        return valorNumerico;
    }

    public boolean getValorLogico() {
        return valorLogico;
    }

    public boolean esLogico() {
        return esLogico;
    }

    public boolean esRelacional() {
        return esRelacional;
    }

    // Debug
    @Override
    public String toString() {
        if (esLogico) {
            return "Logico: " + valorLogico + " | Relacional: " + esRelacional;
        } else {
            return "Numerico: " + valorNumerico;
        }
    }
}

