import java_cup.runtime.*;

%%

%class Scanner
%cup
%unicode
%line
%column

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

// Comment can be the last line of the file, without line terminator.
//EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

//Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*

%state STRING

%%
/* keywords */
//<YYINITIAL> "abstract"           { return symbol(sym.ABSTRACT); }
//<YYINITIAL> "boolean"            { return symbol(sym.BOOLEAN); }
//<YYINITIAL> "break"              { return symbol(sym.BREAK); }

<YYINITIAL> {
/* identifiers */
//{Identifier}                   { return symbol(sym.IDENTIFIER); }

/* literals */
{DecIntegerLiteral}            { return symbol(sym.INTEGER_LITERAL); }

/* operators */
//"="                            { return symbol(sym.EQ); }
//"=="                           { return symbol(sym.EQEQ); }
"+"                            { return symbol(sym.PLUS); }

/* comments */
//{Comment}                      { /* ignore */ }

/* whitespace */
{WhiteSpace}                   { /* ignore */ }
}

/*<STRING> {
\"                             { yybegin(YYINITIAL);
                               return symbol(sym.STRING_LITERAL,
                               string.toString()); }
[^\n\r\"\\]+                   { string.append( yytext() ); }
\\t                            { string.append('\t'); }
\\n                            { string.append('\n'); }

\\r                            { string.append('\r'); }
\\\"                           { string.append('\"'); }
\\                             { string.append('\\'); }
} */

