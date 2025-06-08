package com.yorth21;

public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }

    private void eat(TokenType esperado) {
        if (currentToken.getType() == esperado) {
            currentToken = lexer.nextToken();
        } else {
            throw new RuntimeException("Error sintáctico: se esperaba '" + esperado +
                    "', pero se encontró '" + currentToken.getLexeme() + "'");
        }
    }

    // Metodo principal S -> ELO
    public NoTerminal parse() {
        NoTerminal resultado = S();
        if (currentToken.getType() != TokenType.EOF) {
            throw new RuntimeException("Error: tokens adicionales después del final de la expresión.");
        }
        return resultado;
    }

    private NoTerminal S() {
        // <S>  <ELO> {Resultado}
        NoTerminal resultado = ELO();
        return resultado;
    }

    private NoTerminal ELO() {
        // <ELO> → <EL2> <ELO_L>
        NoTerminal izquierdo = EL2(); // primero evaluamos la parte izquierda
        return ELO_L(izquierdo);      // luego evaluamos si hay más ORs
    }

    private NoTerminal ELO_L(NoTerminal izquierdo) {
        if (currentToken.getType() == TokenType.OR) {
            eat(TokenType.OR);
            NoTerminal derecho = EL2();

            // Validación semántica: ambos operandos deben ser relacionales
            if (!izquierdo.esRelacional() || !derecho.esRelacional()) {
                throw new RuntimeException("Error semántico: OR solo puede operar sobre expresiones relacionales.");
            }

            boolean resultado = izquierdo.getValorLogico() || derecho.getValorLogico();
            NoTerminal combinado = NoTerminal.conValorLogico(resultado, true); // resultado relacional

            return ELO_L(combinado); // llamada recursiva para más ORs
        }

        // Producción: ELO_L → ε
        return izquierdo;
    }

    private NoTerminal EL2() {
        // <EL2> → <ER> <EL2_L>
        NoTerminal izquierdo = ER();      // evaluamos una expresión relacional
        return EL2_L(izquierdo);          // buscamos si hay más ANDs
    }

    private NoTerminal EL2_L(NoTerminal izquierdo) {
        if (currentToken.getType() == TokenType.AND) {
            eat(TokenType.AND);
            NoTerminal derecho = ER();

            // Validar que ambos operandos provienen de relaciones
            if (!izquierdo.esRelacional() || !derecho.esRelacional()) {
                throw new RuntimeException("Error semántico: AND solo puede operar sobre expresiones relacionales.");
            }

            boolean resultado = izquierdo.getValorLogico() && derecho.getValorLogico();
            NoTerminal combinado = NoTerminal.conValorLogico(resultado, true);

            return EL2_L(combinado); // llamada recursiva para más ANDs
        }

        // Producción: EL2_L → ε
        return izquierdo;
    }

    private NoTerminal ER() {
        NoTerminal izquierdo = E();         // expresión aritmética (puede ser número)
        return ER_L(izquierdo);             // si hay operador relacional, lo evaluamos
    }

    private NoTerminal ER_L(NoTerminal izquierdo) {
        TokenType tipo = currentToken.getType();

        if (tipo == TokenType.LESS_THAN || tipo == TokenType.LESS_EQUAL ||
                tipo == TokenType.GREATER_THAN || tipo == TokenType.GREATER_EQUAL ||
                tipo == TokenType.EQUAL_EQUAL || tipo == TokenType.NOT_EQUAL) {

            TokenType operador = tipo;
            eat(operador);
            NoTerminal derecho = E(); // otra expresión aritmética

            // Ambos lados deben ser valores numéricos
            double a = izquierdo.getValorNumerico();
            double b = derecho.getValorNumerico();

            boolean resultado;
            switch (operador) {
                case LESS_THAN:        resultado = a < b; break;
                case LESS_EQUAL:       resultado = a <= b; break;
                case GREATER_THAN:     resultado = a > b; break;
                case GREATER_EQUAL:    resultado = a >= b; break;
                case EQUAL_EQUAL:      resultado = a == b; break;
                case NOT_EQUAL:        resultado = a != b; break;
                default: throw new RuntimeException("Operador relacional inválido");
            }

            return NoTerminal.conValorLogico(resultado, true); // ← relacional = true
        }

        // No hay comparación: devolvemos tal cual
        return izquierdo;
    }

    private NoTerminal E() {
        NoTerminal izquierdo = T();      // Primero multiplicaciones/divisiones
        return E_L(izquierdo);           // Luego revisamos si hay + o -
    }

    private NoTerminal E_L(NoTerminal izquierdo) {
        TokenType tipo = currentToken.getType();

        if (tipo == TokenType.PLUS || tipo == TokenType.MINUS) {
            eat(tipo);
            NoTerminal derecho = T();

            double a = izquierdo.getValorNumerico();
            double b = derecho.getValorNumerico();
            double resultado = tipo == TokenType.PLUS ? a + b : a - b;

            NoTerminal combinado = NoTerminal.conValorNumerico(resultado);
            return E_L(combinado); // Llamada recursiva por si hay más + o -
        }

        // Producción: E_L → ε
        return izquierdo;
    }

    private NoTerminal T() {
        NoTerminal izquierdo = P();         // Primero valores o paréntesis
        return T_L(izquierdo);              // Luego buscamos * o /
    }

    private NoTerminal T_L(NoTerminal izquierdo) {
        TokenType tipo = currentToken.getType();

        if (tipo == TokenType.MULTIPLY || tipo == TokenType.DIVIDE) {
            eat(tipo);
            NoTerminal derecho = P();

            double a = izquierdo.getValorNumerico();
            double b = derecho.getValorNumerico();
            double resultado = tipo == TokenType.MULTIPLY ? a * b : a / b;

            NoTerminal combinado = NoTerminal.conValorNumerico(resultado);
            return T_L(combinado); // Seguimos por si hay más * o /
        }

        // Producción: T_L → ε
        return izquierdo;
    }

    private NoTerminal P() {
        NoTerminal izquierdo = F();       // Un número, paréntesis o subexpresión
        return P_L(izquierdo);            // Aplicar potencias si hay
    }

    private NoTerminal P_L(NoTerminal izquierdo) {
        if (currentToken.getType() == TokenType.POWER) {
            eat(TokenType.POWER);
            NoTerminal derecho = F();

            double base = izquierdo.getValorNumerico();
            double exponente = derecho.getValorNumerico();
            double resultado = Math.pow(base, exponente);

            NoTerminal combinado = NoTerminal.conValorNumerico(resultado);
            return P_L(combinado); // Por si hay más potencias
        }

        // Producción: P_L → ε
        return izquierdo;
    }

    /* Potencia de derecha a izquierda
    private NoTerminal P_L(NoTerminal izquierdo) {
        if (currentToken.getType() == TokenType.POWER) {
            eat(TokenType.POWER);
            NoTerminal derecho = F();

            // Aquí viene la clave: procesar el resto de potencias ANTES de aplicar Math.pow
            NoTerminal exponenteFinal = P_L(derecho);  // ← asociamos hacia la derecha

            double base = izquierdo.getValorNumerico();
            double exponente = exponenteFinal.getValorNumerico();
            double resultado = Math.pow(base, exponente);

            return NoTerminal.conValorNumerico(resultado);
        }

        return izquierdo;
    }
    */

    private NoTerminal F() {
        TokenType tipo = currentToken.getType();

        if (tipo == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            NoTerminal resultado = ELO(); // Aquí puede venir cualquier expresión
            eat(TokenType.RPAREN);
            return resultado;
        }

        if (tipo == TokenType.IDENTIFIER) {
            String lexema = currentToken.getLexeme();
            eat(TokenType.IDENTIFIER);

            try {
                double valor = Double.parseDouble(lexema);
                return NoTerminal.conValorNumerico(valor);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error: identificador no numérico no está soportado: " + lexema);
            }
        }

        throw new RuntimeException("Error sintáctico: se esperaba número o paréntesis, pero se encontró " + currentToken.getLexeme());
    }
}
