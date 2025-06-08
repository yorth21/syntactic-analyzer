package com.yorth21.pila;

public class ResultadoParcial {
    private double valor;
    private String infijo;
    private String postfijo;
    private String prefijo;

    public ResultadoParcial(double valor, String infijo, String postfijo, String prefijo) {
        this.valor = valor;
        this.infijo = infijo;
        this.postfijo = postfijo;
        this.prefijo = prefijo;
    }

    public double getValor() {
        return valor;
    }

    public String getInfijo() {
        return infijo;
    }

    public String getPostfijo() {
        return postfijo;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setInfijo(String infijo) {
        this.infijo = infijo;
    }

    public void setPostfijo(String postfijo) {
        this.postfijo = postfijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    @Override
    public String toString() {
        return "ResultadoParcial{" +
                "valor=" + valor +
                ", infijo='" + infijo + '\'' +
                ", postfijo='" + postfijo + '\'' +
                ", prefijo='" + prefijo + '\'' +
                '}';
    }
}
