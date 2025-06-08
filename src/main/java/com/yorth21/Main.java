package com.yorth21;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Evaluador de Expresiones Lógico-Aritméticas ===");
        System.out.println("Escribe una expresión y presiona Enter.");
        System.out.println("Escribe 'salir' para terminar.\n");

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("salir")) {
                System.out.println("Hasta luego 👋");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            try {
                Lexer lexer = new Lexer(input);
                Parser parser = new Parser(lexer);
                NoTerminal resultado = parser.parse();

                System.out.println("✅ Expresión válida.");
                if (resultado.esLogico()) {
                    System.out.println("Resultado lógico: " + resultado.getValorLogico());
                } else {
                    System.out.println("Resultado numérico: " + resultado.getValorNumerico());
                }
            } catch (RuntimeException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

            System.out.println(); // Espacio entre consultas
        }

        scanner.close();
    }
}


