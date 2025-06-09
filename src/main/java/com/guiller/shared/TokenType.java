package com.guiller.shared;

public enum TokenType {
    // Operadores aritméticos
    PLUS,       // +
    MINUS,      // -
    MULTIPLY,   // *
    DIVIDE,     // /
    POWER,      // ^

    // Operadores relacionales
    LESS_THAN,          // <
    GREATER_THAN,       // >
    LESS_EQUAL,         // <=
    GREATER_EQUAL,      // >=
    EQUAL_EQUAL,        // ==
    NOT_EQUAL,          // !=

    // Operadores lógicos
    AND,        // &
    OR,         // |
    NOT,        // ¬ o NOT

    // Paréntesis
    LPAREN,     // (
    RPAREN,     // )

    // Identificadores y números
    IDENTIFIER, // Ej: x, y, 25.5

    // Fin de entrada
    EOF,

    // Token inválido (por si acaso)
    INVALID
}
