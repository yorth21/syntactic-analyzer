package com.guiller;

import com.guiller.pila.AnalizadorPila;
import com.guiller.shared.NoTerminal;
import com.guiller.recursive.Parser;
import com.guiller.shared.Lexer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    === Analizador de Expresiones ===
                    1. Analizador Recursivo
                    2. Analizador con Pila
                    3. Salir
                    """);
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine().trim();

            if (opcion.equals("3")) {
                System.out.println("Hasta luego 👋");
                break;
            }

            System.out.print("Ingrese una expresión: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("⚠️ Entrada vacía. Intente de nuevo.\n");
                continue;
            }

            try {
                switch (opcion) {
                    case "1" -> {
                        Lexer lexer = new Lexer(input);
                        Parser parser = new Parser(lexer);
                        NoTerminal resultado = parser.parse();

                        System.out.println("✅ Expresión válida.");
                        if (resultado.esLogico()) {
                            System.out.println("Resultado lógico: " + resultado.getValorLogico());
                        } else {
                            System.out.println("Resultado numérico: " + resultado.getValorNumerico());
                        }
                    }

                    case "2" -> {
                        Lexer lexer = new Lexer(input);
                        AnalizadorPila analizador = new AnalizadorPila(lexer);
                        analizador.analizar(); // ya imprime su propio resultado
                    }

                    default -> System.out.println("⚠️ Opción no válida.\n");
                }

            } catch (RuntimeException e) {
                System.out.println("❌ Error: " + e.getMessage() + "\n");
            }
        }

        scanner.close();
    }
}
