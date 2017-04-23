/**
 * This is a simple example of a jflex lexer definition
 */

/* Declarations */


%%

%class  MyScanner   /* Names the produced java file */
%function nextToken /* Renames the yylex() function */
%type   Token      /* Defines the return type of the scanning function */
%eofval{
  return null;
%eofval}
%{
  LookUpTable map = new LookUpTable();
%}
/* Patterns */

letter        = [A-Za-z]
digit         = [0-9]
whitespace    = ([ \n\t]|[{]+.*+[}])
id	      = {letter}+({letter}|{digit}|[_])*
digits	      = {digit}+
optional_fraction = [.]{digits}
optional_exponent = [E]([+]|[-])?{digits}
num 	      = {digits}{optional_fraction}?{optional_exponent}?
symbol	      = [+*-/\\<>=\(\)\[\],:;]
mSym	      = (([<]+[>])|([<>:]+[=]))
symbols	      = ({symbol}|{mSym})

%%
/* Lexical Rules */

{num} 	{
	  return new Token(yytext(), Keywords.NUMBER);
	}
{id}    {
          Keywords key = map.get(yytext());
	  if(key == null){
	    return new Token(yytext(), Keywords.ID);
	  }
	  else{
            return new Token(yytext(), key);
	  }
        }
{symbols} {
	  Keywords key = map.get(yytext());
	  return new Token(yytext(), key);
	 }
            
{whitespace}  {  /* Ignore Whitespace */ 
		System.out.println("Whitespace/Comment: '" + yytext());
              }
.	{ 
             System.out.println("Illegal Char: '" + yytext());
           }
           
