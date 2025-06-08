package com.yorth21.shared;

public class Simbolo {
    private final String nombre;
    private final TipoSimbolo tipo;

    public Simbolo(String nombre, TipoSimbolo tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoSimbolo getTipo() {
        return tipo;
    }

    public boolean esTerminal() {
        return tipo == TipoSimbolo.TERMINAL;
    }

    public boolean esNoTerminal() {
        return tipo == TipoSimbolo.NO_TERMINAL;
    }

    public boolean esAccion() {
        return tipo == TipoSimbolo.ACCION;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Simbolo) {
            Simbolo otro = (Simbolo) obj;
            return this.nombre.equals(otro.nombre) && this.tipo == otro.tipo;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nombre.hashCode() + tipo.hashCode();
    }
}

