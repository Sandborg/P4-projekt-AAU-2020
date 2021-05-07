import java_cup.runtime.*;

%%

%class Scanner
%cup
%line
%unicode
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
IdentifierVal = [:jletter:] [:jletterdigit:]*\.val
IdentifierAdr =  [:jletter:] [:jletterdigit:]*\.adr

Int = 0 | [1-9][0-9]*
Decimal = [0-9]*\.[0-9]+

%state STRING

%%
/* keywords */
<YYINITIAL> "int"       { return symbol(sym.INT_TYPE);      }
<YYINITIAL> "decimal"   { return symbol(sym.DECIMAL_TYPE);  }
<YYINITIAL> "string"    { return symbol(sym.STRING_TYPE);   }
<YYINITIAL> "void"      { return symbol(sym.VOID_TYPE);     }
<YYINITIAL> "prototype" { return symbol(sym.PROTOTYPE);     }
<YYINITIAL> "return"    { return symbol(sym.RETURN);        }
<YYINITIAL> "import"    { return symbol(sym.IMPORT);        }
<YYINITIAL> "if"        { return symbol(sym.IF);            }
<YYINITIAL> "else"      { return symbol(sym.ELSE);          }

<YYINITIAL> "IS"        { return symbol(sym.IS);            }
<YYINITIAL> "NOT"       { return symbol(sym.NOT);           }
<YYINITIAL> "OR"        { return symbol(sym.OR);            }
<YYINITIAL> "AND"       { return symbol(sym.AND);           }

<YYINITIAL> {
/* identifiers */
{Identifier}    { return symbol(sym.IDENTIFIER, yytext());                              }
{IdentifierVal} { return symbol(sym.IDENTIFIERVAL, yytext());                           }
{IdentifierAdr} { return symbol(sym.IDENTIFIERADR, yytext());                           }
/* literals */
{Int}           { return symbol(sym.INTEGER, new Integer(Integer.parseInt(yytext()))); }
{Decimal}       { return symbol(sym.DECIMAL, new Float(Float.parseFloat(yytext())));   }
\"              { string.setLength(0); yybegin(STRING); }

/* operators */
"+"				{ return symbol(sym.PLUS);          }
"-"				{ return symbol(sym.MINUS);         }
"*"				{ return symbol(sym.TIMES);         }
"/"             { return symbol(sym.DIVIDE);        }
"%"             { return symbol(sym.MOD);           }
"="             { return symbol(sym.EQUALS);        }

/* logical operators */
">"             { return symbol(sym.GREATER);       }
"<"             { return symbol(sym.LESSER);        }
">="            { return symbol(sym.GREATER_EQUALS); }
"<="            { return symbol(sym.LESSER_EQUALS);  }


/* reserved symbols */
"("             { return symbol(sym.LEFT_PAREN);    }
")"             { return symbol(sym.RIGHT_PAREN);   }
"{"             { return symbol(sym.LEFT_CURLY);    }
"}"             { return symbol(sym.RIGHT_CURLY);   }
","             { return symbol(sym.COMMA);         }
";" 		    { return symbol(sym.SEMI);          }

/* comments */

/* whitespace */
{WhiteSpace}                   { /* ignore */ }
}

    <STRING> {
      \"                             { yybegin(YYINITIAL);
                                       return symbol(sym.STRING_LITERAL,
                                       string.toString()); }
      [^\n\r\"\\]+                   { string.append( yytext() ); }
      \\t                            { string.append('\t'); }
      \\n                            { string.append('\n'); }

      \\r                            { string.append('\r'); }
      \\\"                           { string.append('\"'); }
      \\                             { string.append('\\'); }
    }



