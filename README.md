# Crafting Interpriters

BNF

```
expression  -> equiality ;
equality    -> comparison ( ("!=" | "==") comparison)* ;
comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        -> factor ( ( "-" | "+" ) factor )* ;
factory     -> unary ( ("/" | "*") unary )* ;
unary       -> ( "!" | "-" ) unary | primary ;
primary     -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;

program     -> declaration* EOF ;
declaration -> varDecl | statement ;
statement   -> exprStmt | printStmt ;
exprStmt    -> expression ";" ;
printStmt   -> "print" expression ";" ;
varDecl     -> "var" IDENTIFIER ( "=" expression )? ";" ;
```