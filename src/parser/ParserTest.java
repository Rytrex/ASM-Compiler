package parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {
	Parser parse;
	String input;
	
    @Test
    public void program() throws Exception {
    	input = "program foo ; var fee : integer ; begin end .";
    	parse = new Parser(input, false);
    	parse.program();
    }

    @Test
    public void declarations() throws Exception {
    	//Single var
    	input = "var fee : integer ;";
    	parse = new Parser(input, false);
    	parse.declarations();
    	//Multiple vars
    	input = "var fee, fi : integer ;";
    	parse = new Parser(input, false);
    	parse.declarations();
    	//Lambda
    	input = ".";
    	parse = new Parser(input, false);
    	parse.declarations();
    }

    @Test
    public void subprogram_declaration() throws Exception {
    	//function id ( parameter_list ) : real ; compound_statement
    	input = "function foo ( fee : integer ) : real ; begin end";
    	parse = new Parser(input, false);
    	parse.subprogram_declaration();
    	//function id lambda : integer ; declartions compound_statement
    	input = "function foo : integer ; var fi : integer ; begin end";
    	parse = new Parser(input, false);
    	parse.subprogram_declaration();
    	//procedure id ( array [ num : num ] of real ) ; compound_statement
    	input = "procedure fi ( foo : array [ 1 : 2 ] of real ) ; begin end";
    	parse = new Parser(input, false);
    	parse.subprogram_declaration();
    	//procedure id lambda ; compound_statement
    	input = "procedure id ; begin end";
    	parse = new Parser(input, false);
    	parse.subprogram_declaration();
    }

    @Test
    public void statement() throws Exception {
    	//variable assignop expression
    	input = "foo [ 1  ] := 2 + 4";
    	parse = new Parser(input, false);
    	parse.statement();
    	//procedure_statement
    	input = "foo (2 + 3)";
    	parse = new Parser(input, false);
    	parse.statement();
    	//variable and procedure_statement id only
    	input = "foo";
    	parse = new Parser(input, false);
    	parse.statement();
    	//compound_statement with optional_statement
    	input = "begin foo := 3 end";
    	parse = new Parser(input, false);
    	parse.statement();
    	//if expression then while expression do statement else statement
    	input = "if 4 > 3 then while 4 < 5 do foo else foo";
    	parse = new Parser(input, false);
    	parse.statement();
    }

    @Test
    public void simple_expression() throws Exception {
    	//id addop id
    	input = "foo + bar";
    	parse = new Parser(input, false);
    	parse.simple_expression();
    	//id addop id addop id
    	input = "foo + bar + cool";
    	parse = new Parser(input, false);
    	parse.simple_expression();
    	//sign id addop id
    	input = "- foo + bar";
    	parse = new Parser(input, false);
    	parse.simple_expression();
    	//sign id addop id
    	input = " + foo + bar";
    	parse = new Parser(input, false);
    	parse.simple_expression();
    }

    @Test
    public void factor() throws Exception {
    	//id
    	input = "foo";
    	parse = new Parser(input, false);
    	parse.factor();
    	//id [ expression ]
    	input = "foo [ 3 + 2 ]";
    	parse = new Parser(input, false);
    	parse.factor();
    	//id ( expression )
    	input = "foo ( 3 + 2 )";
    	parse = new Parser(input, false);
    	parse.factor();
    	//num
    	input = "4";
    	parse = new Parser(input, false);
    	parse.factor();
    	//( expression )
    	input = "( 3 + 2 )";
    	parse = new Parser(input, false);
    	parse.factor();
    	//not id
    	input = "not foo";
    	parse = new Parser(input, false);
    	parse.factor();
    }
	
	@Test
	public void test() {
		parse = new Parser("/home/vod/workspace/Compiler/src/scanner/simplest.pas", true);
		parse.program();
	}
	
	@Test
	public void test2() {
		parse = new Parser("/home/vod/workspace/Compiler/src/scanner/simple.pas", true);
		parse.program();
	}
}
