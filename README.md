# Crafting Interpriters

BNF

```
expression  -> assignment ;
assignment  -> ( call "." )? IDENTIFIER "=" assigngment | logic_or ;
logic_or    -> logic_and ( "or" logic_ant )* ;
logic_and   -> equality ( "and" equality )* ;
equality    -> comparison ( ("!=" | "==") comparison)* ;
comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        -> factor ( ( "-" | "+" ) factor )* ;
factory     -> unary ( ("/" | "*") unary )* ;
unary       -> ( "!" | "-" ) unary | call ;
call        -> primary ( "(" arguments? ")" | "." IDENTIFIER )* ;
arguments   -> expression ( "," expression )* ;
primary     -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER | "super" "." IDENTIFIER;

program     -> declaration* EOF ;
declaration -> classDecl | funDecl | varDecl | statement ;
statement   -> exprStmt | forStmt | ifStmt | printStmt | whileStmt | block ;
block       -> "{" declaration* "}" ;
exprStmt    -> expression ";" ;
printStmt   -> "print" expression ";" ;
varDecl     -> "var" IDENTIFIER ( "=" expression )? ";" ;
classDecl   -> "class" IDENTIFIER ( "<" IDENTIFIER )? "{" function* "}" ;
funDecl     -> "fun" function ;
function    -> IDENTIFIER "(" parameters? ")" block ;
parameters  -> IDENTIFIER ( "," IDENTIFIER )* ;
ifStmt      -> "if" "(" expression ")" statement ( "else" statement )? ;
whileStmt   -> "while" "(" expression ")" statement ;
forStmt     -> "for" "(" ( varDecl | exprStmt | ";" ) expression? ";" expression? ")" statement ;
returnStmt  -> "return" expression? ";" ;
```