package com.yorth21;

public class Lexer {
    private final String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    private char peek() {
        if (position >= input.length()) return '\0';
        return input.charAt(position);
    }

    private char advance() {
        return input.charAt(position++);
    }

    private boolean match(char expected) {
        if (peek() == expected) {
            position++;
            return true;
        }
        return false;
    }

    public Token nextToken() {
        while (Character.isWhitespace(peek())) advance();

        if (position >= input.length()) return new Token(TokenType.EOF, "");

        char current = advance();

        switch (current) {
            case '+': return new Token(TokenType.PLUS, "+");
            case '-': return new Token(TokenType.MINUS, "-");
            case '*': return new Token(TokenType.MULTIPLY, "*");
            case '/': return new Token(TokenType.DIVIDE, "/");
            case '^': return new Token(TokenType.POWER, "^");
            case '(': return new Token(TokenType.LPAREN, "(");
            case ')': return new Token(TokenType.RPAREN, ")");
            case '<':
                if (match('=')) return new Token(TokenType.LESS_EQUAL, "<=");
                return new Token(TokenType.LESS_THAN, "<");
            case '>':
                if (match('=')) return new Token(TokenType.GREATER_EQUAL, ">=");
                return new Token(TokenType.GREATER_THAN, ">");
            case '=':
                if (match('=')) return new Token(TokenType.EQUAL_EQUAL, "==");
                break;
            case '!':
                if (match('=')) return new Token(TokenType.NOT_EQUAL, "!=");
                break;
            case '&': return new Token(TokenType.AND, "&");
            case '|': return new Token(TokenType.OR, "|");
            case '¬': return new Token(TokenType.NOT, "¬"); // alternativo para NOT
        }

        if (Character.isDigit(current)) {
            StringBuilder number = new StringBuilder();
            number.append(current);
            while (Character.isDigit(peek()) || peek() == '.') {
                number.append(advance());
            }
            return new Token(TokenType.IDENTIFIER, number.toString());
        }

        if (Character.isLetter(current)) {
            StringBuilder word = new StringBuilder();
            word.append(current);
            while (Character.isLetterOrDigit(peek())) {
                word.append(advance());
            }

            String wordStr = word.toString().toUpperCase();

            return switch (wordStr) {
                case "AND" -> new Token(TokenType.AND, wordStr);
                case "OR" -> new Token(TokenType.OR, wordStr);
                case "NOT" -> new Token(TokenType.NOT, wordStr);
                default -> new Token(TokenType.IDENTIFIER, wordStr);
            };
        }

        return new Token(TokenType.INVALID, String.valueOf(current));
    }
}
