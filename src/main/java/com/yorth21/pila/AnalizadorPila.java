package com.yorth21.pila;

import com.yorth21.shared.Lexer;
import com.yorth21.shared.Token;
import com.yorth21.shared.TokenType;
import com.yorth21.shared.Simbolo;
import com.yorth21.shared.TipoSimbolo;

import java.util.*;

public class AnalizadorPila {
    private final Lexer lexer;
    private Token currentToken;

    private final Stack<Simbolo> pila = new Stack<>();
    private final Stack<ResultadoParcial> atributos = new Stack<>();

    private final Map<String, Map<TokenType, List<Simbolo>>> tabla = new HashMap<>();

    public AnalizadorPila(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
        inicializarTabla(); // llenaremos esto después
    }

    public void analizar() {
        pila.push(new Simbolo("$", TipoSimbolo.TERMINAL));
        pila.push(new Simbolo("S", TipoSimbolo.NO_TERMINAL));

        while (!pila.isEmpty()) {
            Simbolo cima = pila.peek();

            if (cima.getTipo() == TipoSimbolo.TERMINAL) {
                if (match(cima.getNombre())) {
                    if (cima.getNombre().equals("I")) {
                        try {
                            double valor = Double.parseDouble(currentToken.getLexeme());
                            ResultadoParcial rp = new ResultadoParcial(
                                    valor,
                                    currentToken.getLexeme(),
                                    currentToken.getLexeme(),
                                    currentToken.getLexeme()
                            );
                            atributos.push(rp);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Error: identificador no numérico: " + currentToken.getLexeme());
                        }
                    }

                    pila.pop();
                    currentToken = lexer.nextToken();
                } else {
                    throw new RuntimeException("Error sintáctico: se esperaba '" + cima.getNombre() +
                            "', pero se encontró '" + currentToken.getLexeme() + "'");
                }
            } else if (cima.getTipo() == TipoSimbolo.ACCION) {
                pila.pop();
                ejecutarAccion(cima.getNombre());
            } else {
                aplicarProduccion(cima);
            }
        }

        if (currentToken.getType() != TokenType.EOF) {
            throw new RuntimeException("Error: tokens adicionales después de finalizar el análisis.");
        }

        atributos.pop();
    }

    private boolean match(String nombreTerminal) {
        TokenType tipo = currentToken.getType();
        return switch (nombreTerminal) {
            case "+" -> tipo == TokenType.PLUS;
            case "-" -> tipo == TokenType.MINUS;
            case "*" -> tipo == TokenType.MULTIPLY;
            case "/" -> tipo == TokenType.DIVIDE;
            case "^" -> tipo == TokenType.POWER;
            case "(" -> tipo == TokenType.LPAREN;
            case ")" -> tipo == TokenType.RPAREN;
            case "I" -> tipo == TokenType.IDENTIFIER;
            case "$" -> tipo == TokenType.EOF;
            default -> false;
        };
    }

    private void agregar(String noTerminal, TokenType terminal, List<Simbolo> produccion) {
        tabla.computeIfAbsent(noTerminal, k -> new HashMap<>()).put(terminal, produccion);
    }

    private Simbolo t(String nombre) {
        return new Simbolo(nombre, TipoSimbolo.TERMINAL);
    }

    private Simbolo nt(String nombre) {
        return new Simbolo(nombre, TipoSimbolo.NO_TERMINAL);
    }

    private Simbolo accion(String nombre) {
        return new Simbolo(nombre, TipoSimbolo.ACCION);
    }

    private void inicializarTabla() {
        agregar("S", TokenType.LPAREN, List.of(nt("E"), accion("{respuesta}")));
        agregar("S", TokenType.IDENTIFIER, List.of(nt("E"), accion("{respuesta}")));

        agregar("E", TokenType.LPAREN, List.of(nt("T"), nt("E-L")));
        agregar("E", TokenType.IDENTIFIER, List.of(nt("T"), nt("E-L")));

        agregar("E-L", TokenType.PLUS, List.of(t("+"), nt("T"), accion("{suma}"), nt("E-L")));
        agregar("E-L", TokenType.MINUS, List.of(t("-"), nt("T"), accion("{resta}"), nt("E-L")));
        agregar("E-L", TokenType.RPAREN, List.of());  // ε
        agregar("E-L", TokenType.EOF, List.of());     // ε

        agregar("T", TokenType.LPAREN, List.of(nt("P"), nt("T-L")));
        agregar("T", TokenType.IDENTIFIER, List.of(nt("P"), nt("T-L")));

        agregar("T-L", TokenType.PLUS, List.of());   // ε
        agregar("T-L", TokenType.MINUS, List.of());  // ε
        agregar("T-L", TokenType.MULTIPLY, List.of(t("*"), nt("P"), accion("{mul}"), nt("T-L")));
        agregar("T-L", TokenType.DIVIDE, List.of(t("/"), nt("P"), accion("{div}"), nt("T-L")));
        agregar("T-L", TokenType.RPAREN, List.of());  // ε
        agregar("T-L", TokenType.EOF, List.of());     // ε

        agregar("P", TokenType.LPAREN, List.of(nt("F"), nt("P-L")));
        agregar("P", TokenType.IDENTIFIER, List.of(nt("F"), nt("P-L")));

        agregar("P-L", TokenType.PLUS, List.of());   // ε
        agregar("P-L", TokenType.MINUS, List.of());  // ε
        agregar("P-L", TokenType.MULTIPLY, List.of());  // ε
        agregar("P-L", TokenType.DIVIDE, List.of());    // ε
        agregar("P-L", TokenType.RPAREN, List.of());    // ε
        agregar("P-L", TokenType.EOF, List.of());       // ε
        agregar("P-L", TokenType.POWER, List.of(t("^"), nt("F"), accion("{exp}"), nt("P-L")));

        agregar("F", TokenType.LPAREN, List.of(t("("), nt("E"), t(")")));
        agregar("F", TokenType.IDENTIFIER, List.of(t("I")));
    }

    private void aplicarProduccion(Simbolo cima) {
        String noTerminal = cima.getNombre();
        TokenType tokenActual = currentToken.getType();

        Map<TokenType, List<Simbolo>> fila = tabla.get(noTerminal);

        if (fila == null || !fila.containsKey(tokenActual)) {
            throw new RuntimeException("Error sintáctico: no hay producción para <" + noTerminal + "> con token '" + currentToken.getLexeme() + "'");
        }

        List<Simbolo> produccion = fila.get(tokenActual);
        pila.pop(); // quitamos el no terminal de la pila

        // Agregamos la producción en orden inverso
        ListIterator<Simbolo> it = produccion.listIterator(produccion.size());
        while (it.hasPrevious()) {
            pila.push(it.previous());
        }
    }

    private void ejecutarAccion(String nombre) {
        switch (nombre) {
            case "{suma}" -> {
                ResultadoParcial b = atributos.pop();
                ResultadoParcial a = atributos.pop();
                double r = a.getValor() + b.getValor();
                atributos.push(new ResultadoParcial(
                        r,
                        "(" + a.getInfijo() + " + " + b.getInfijo() + ")",
                        a.getPostfijo() + " " + b.getPostfijo() + " +",
                        "+ " + a.getPrefijo() + " " + b.getPrefijo()
                ));
            }
            case "{resta}" -> {
                ResultadoParcial b = atributos.pop();
                ResultadoParcial a = atributos.pop();
                double r = a.getValor() - b.getValor();
                atributos.push(new ResultadoParcial(
                        r,
                        "(" + a.getInfijo() + " - " + b.getInfijo() + ")",
                        a.getPostfijo() + " " + b.getPostfijo() + " -",
                        "- " + a.getPrefijo() + " " + b.getPrefijo()
                ));
            }
            case "{mul}" -> {
                ResultadoParcial b = atributos.pop();
                ResultadoParcial a = atributos.pop();
                double r = a.getValor() * b.getValor();
                atributos.push(new ResultadoParcial(
                        r,
                        "(" + a.getInfijo() + " * " + b.getInfijo() + ")",
                        a.getPostfijo() + " " + b.getPostfijo() + " *",
                        "* " + a.getPrefijo() + " " + b.getPrefijo()
                ));
            }
            case "{div}" -> {
                ResultadoParcial b = atributos.pop();
                ResultadoParcial a = atributos.pop();
                double r = a.getValor() / b.getValor();
                atributos.push(new ResultadoParcial(
                        r,
                        "(" + a.getInfijo() + " / " + b.getInfijo() + ")",
                        a.getPostfijo() + " " + b.getPostfijo() + " /",
                        "/ " + a.getPrefijo() + " " + b.getPrefijo()
                ));
            }
            case "{exp}" -> {
                ResultadoParcial b = atributos.pop();
                ResultadoParcial a = atributos.pop();
                double r = Math.pow(a.getValor(), b.getValor());
                atributos.push(new ResultadoParcial(
                        r,
                        "(" + a.getInfijo() + " ^ " + b.getInfijo() + ")",
                        a.getPostfijo() + " " + b.getPostfijo() + " ^",
                        "^ " + a.getPrefijo() + " " + b.getPrefijo()
                ));
            }
            case "{respuesta}" -> {
                ResultadoParcial res = atributos.peek();
                imprimirResultado(res);
            }
            default -> throw new RuntimeException("Acción no reconocida: " + nombre);
        }
    }

    private void imprimirResultado(ResultadoParcial resultado) {
        System.out.println("✅ Expresión válida.");
        System.out.println("Resultado: " + resultado.getValor());
        System.out.println("Infijo:    " + resultado.getInfijo());
        System.out.println("Postfijo:  " + resultado.getPostfijo());
        System.out.println("Prefijo:   " + resultado.getPrefijo());
    }
}
