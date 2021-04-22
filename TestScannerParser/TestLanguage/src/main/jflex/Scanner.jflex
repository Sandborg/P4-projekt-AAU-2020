import java_cup.runtime.*;

%%

%class Scanner
%cup
%line
%column
%char


%{
StringBuffer string = new StringBuffer();

private Symbol symbol(int type) {
return new Symbol(type, yyline, yycolumn);
}
private Symbol symbol(int type, Object value) {
return new Symbol(type, yyline, yycolumn, value);
}
%}

LineTerminator = \r|\n|\r\n
//InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
//Comment = {EndOfLineComment}

Identifier = [:jletter:] [:jletterdigit:]*

Int = 0 | [1-9][0-9]*
Decimal = [0-9]*\.[0-9]+

%%
/* keywords */
<YYINITIAL> "int"       { return symbol(sym.INT_TYPE);}
<YYINITIAL> "decimal"   { return symbol(sym.DECIMAL_TYPE);}
<YYINITIAL> "prototype" { return symbol(sym.PROTOTYPE);}

<YYINITIAL> {
/* identifiers */
{Identifier}               { return symbol(sym.IDENTIFIER, yytext()); }

/* literals */
{Int}            { return symbol(sym.INTEGER, new Integer(Integer.parseInt(yytext()))); }
{Decimal}                       {return symbol(sym.DECIMAL, new Float(Float.parseFloat(yytext())));}

/* operators */

"+"				{ return symbol(sym.PLUS); }
"-"				{ return symbol(sym.MINUS); }
"*"				{ return symbol(sym.TIMES); }
"/"             { return symbol(sym.DIVIDE);}
"%"             { return symbol(sym.MOD);}
"="             { return symbol(sym.EQUALS);}

/* reserved symbols */
"("         { return symbol(sym.LEFT_PAREN);}
")"         { return symbol(sym.RIGHT_PAREN);}
"{"         { return symbol(sym.LEFT_CURLY);}
"}"         { return symbol(sym.RIGHT_CURLY);}
","         {return symbol(sym.COMMA);}
";" 		{ return symbol(sym.SEMI); }
/* comments */


/* whitespace */
{WhiteSpace}                   { /* ignore */ }
}



