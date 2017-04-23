package scanner;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

public class MyScannerTest {

	FileInputStream fis;
	InputStreamReader isr;
	FileInputStream fis2;
	InputStreamReader isr2;
	
	@Before
	/**
	 * Used to set up the test methods InputStreamReaders
	 * @throws Exception
	 */
	public void setUp() throws Exception {
		fis = new FileInputStream("/home/vod/workspace/Compiler/src/scanner/simplest.pas");
		isr = new InputStreamReader( fis);
		fis2 = new FileInputStream("/home/vod/workspace/Compiler/src/scanner/simple.pas");
		isr2 = new InputStreamReader( fis2);
	}

	@Test
	/**
	 * Testing yytext() using simplest.pas
	 * @throws Exception
	 */
	public void testYytext() throws Exception {
		MyScanner scan = new MyScanner( isr);
		String foo = scan.yytext();
		assertEquals("", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("program", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("foo" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(";" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("begin" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("end" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("." ,foo);
	}

	@Test
	/**
	 * Testing nextToken() using simplest.pas
	 * @throws Exception
	 */
	public void testNextToken() throws Exception {
		MyScanner scan = new MyScanner( isr);
		Token foo = scan.nextToken();
		assertEquals(TokenType.PROGRAM ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.SEMI_COLON ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.BEGIN ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.END ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.PERIOD ,foo.getType());
	}
	
	@Test
	/**
	 * Testing yytext() using simple.pas
	 * @throws Exception
	 */
	public void testYytext2() throws Exception {
		MyScanner scan = new MyScanner( isr2);
		String foo = scan.yytext();
		assertEquals("", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("program", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("foo", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(";" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("var" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fee" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("," ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fi" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(",", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fo" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("," ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fum" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(":" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("integer" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(";", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("begin" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fee" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(":=" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("4" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(";" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fi", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(":=" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("5" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(";" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fo" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(":=" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("3", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("*" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fee" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("+" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fi" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(";" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("if", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fo" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("<" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("13" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("then" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fo" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(":=", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("13" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("else" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fo" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(":=" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("26" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(";", foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("write" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("(" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("fo" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals(")" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("end" ,foo);
		scan.nextToken();
		foo = scan.yytext();
		assertEquals("." ,foo);
	}

	@Test
	/**
	 * Testing nextToken() using simple.pas
	 * @throws Exception
	 */
	public void testNextToken2() throws Exception {
		MyScanner scan = new MyScanner( isr2);
		Token foo = scan.nextToken();
		assertEquals(TokenType.PROGRAM ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.SEMI_COLON ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.VAR ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COMMA ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COMMA ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COMMA ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COLON ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.INTEGER ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.SEMI_COLON ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.BEGIN ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COLON_EQUALS_TO ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.NUMBER ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.SEMI_COLON ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COLON_EQUALS_TO ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.NUMBER ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.SEMI_COLON ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COLON_EQUALS_TO ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.NUMBER ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ASTERISK ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.PLUS ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.SEMI_COLON ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.IF ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.LESS_THAN ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.NUMBER ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.THEN ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COLON_EQUALS_TO ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.NUMBER ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ELSE ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.COLON_EQUALS_TO ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.NUMBER ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.SEMI_COLON ,foo.getType());
		foo = scan.nextToken();
		//TODO: check write
		//assertEquals(TokenType.WRITE ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.LEFT_PARENTHESIS ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.ID ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.RIGHT_PARENTHESIS ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.END ,foo.getType());
		foo = scan.nextToken();
		assertEquals(TokenType.PERIOD ,foo.getType());
	}

}
