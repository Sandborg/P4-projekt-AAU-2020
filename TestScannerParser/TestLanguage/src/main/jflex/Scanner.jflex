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



DecIntegerLiteral = 0 | [1-9][0-9]*


%%
/* keywords */
<YYINITIAL> ";" 		{ return symbol(sym.SEMI); }
<YYINITIAL> "-1" 		{ return symbol(sym.EOF); }
<YYINITIAL> {
/* identifiers */
//{Identifier}                   { return symbol(sym.IDENTIFIER); }

/* literals */
{DecIntegerLiteral}            { return symbol(sym.NUMBER, new Integer(Integer.parseInt(yytext()))); }

/* operators */

"+"				{ return symbol(sym.PLUS); }
"-"				{ return symbol(sym.MINUS); }
"*"				{ return symbol(sym.TIMES); }
"/"             { return symbol(sym.DIVIDE);}
"%"             { return symbol(sym.MOD);}
/* comments */


/* whitespace */
{WhiteSpace}                   { /* ignore */ }
}



