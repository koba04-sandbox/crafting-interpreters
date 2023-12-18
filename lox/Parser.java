package lox;

import static lox.TokenType.BANG;
import static lox.TokenType.BANG_EQUAL;
import static lox.TokenType.EOF;
import static lox.TokenType.EQUAL_EQUAL;
import static lox.TokenType.FALSE;
import static lox.TokenType.GREATER;
import static lox.TokenType.GREATER_EQUAL;
import static lox.TokenType.LEFT_PAREN;
import static lox.TokenType.LESS;
import static lox.TokenType.LESS_EQUAL;
import static lox.TokenType.MINUS;
import static lox.TokenType.NIL;
import static lox.TokenType.NUMBER;
import static lox.TokenType.PLUS;
import static lox.TokenType.RIGHT_PAREN;
import static lox.TokenType.SLASH;
import static lox.TokenType.STAR;
import static lox.TokenType.STRING;
import static lox.TokenType.TRUE;

import java.util.List;

public class Parser {
    private final List<Token>  tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // expression  -> equiality ;
    private Expr expression() {
        return equality();
    }

    // equality    -> comparison ( ("!=" | "==") comparison)* ;
    private Expr equality() {
        Expr expr = comparison();
        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    // comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    // term        -> factor ( ( "-" | "+" ) factor )* ;
    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right)
        }

        return expr;
    }

    // factory     -> unary ( ("/" | "*") unary )* ;
    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right)
        }

        return expr;
    }

    // unary       -> ( "!" | "-" ) unary | primary ;
    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return primary();
        }
    }

    // primary     -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" ;
    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }

    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}