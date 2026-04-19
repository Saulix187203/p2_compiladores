package com.sv;
import java_cup.runtime.*;

%%
%public
%class Lexer
%cup

// caracteres básicos
LETRA = [a-zA-Z]+
NUMERO = [0-9]+
SIMBOLOS= [_,#,$,%,¡]+
ARITMETICOS= [+,-,*,/]
COMPARADORES= [=,!,<,>]
ESPACIO=[ , \t,\r,\n]+ // espacios de tabuladores

//valores primitivos y no primitivos
ENTERO = ("-"{NUMERO} |{NUMERO})
DECIMAL = (-?{NUMERO}+\.{NUMERO}+)
VALOR = "\"" ( ( {LETRA} | {NUMERO} | {SIMBOLOS}+|{ARITMETICOS}+|{COMPARADORES}+ )* ) "\""

%{
    StringBuffer buffer = new StringBuffer();
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

%eofval{
  return new Symbol(ParserSym.EOF);
%eofval}

%%

"{"        { return symbol(ParserSym.LLAVE_O, yytext()); }
"}"        { return symbol(ParserSym.LLAVE_I, yytext()); }
"["        { return symbol(ParserSym.BRACKET_O, yytext()); }
"]"        { return symbol(ParserSym.BRACKET_I, yytext()); }
":"        { return symbol(ParserSym.SEPARADOR, yytext()); }
","        { return symbol(ParserSym.COMA, yytext()); }
"null"     { return symbol(ParserSym.NULO, yytext()); }
"true"     { return symbol(ParserSym.BOOLEAN, true); }
"false"    { return symbol(ParserSym.BOOLEAN, false); }
{ENTERO}         { return symbol(ParserSym.INTEGER, Integer.valueOf(yytext())); }
{DECIMAL}        { return symbol(ParserSym.DECIMAL, Float.valueOf(yytext())); }
{VALOR}         { return symbol(ParserSym.STRING, yytext()); }
{ESPACIO}+       {/*Ignorar espacios en blanco*/}
[^]              {throw new Error("Caracter no reconocido: " + yytext()); }
.          { throw new Error("Caracter no reconocido: " + yytext()); }

