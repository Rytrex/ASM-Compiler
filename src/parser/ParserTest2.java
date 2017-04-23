package parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import syntaxtree.ProgramNode;

public class ParserTest2 {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void programTest() {
	    Parser parser = new Parser( "/home/vod/workspace/Compiler/src/scanner/money.pas", true);
	    ProgramNode actual = parser.program();
	    String actualString = actual.indentedToString( 0);
	    String expectedString =
	    
	"Program: sample\n" +
	"|-- Declarations\n" +
	"|-- --- Name: dollars\n" +
	"|-- --- Name: yen\n" +
	"|-- --- Name: bitcoins\n" +
	"|-- SubProgramDeclarations\n" +
	"|-- Compound Statement\n" +
	"|-- --- Assignment\n" +
	"|-- --- --- Name: dollars\n" +
	"|-- --- --- Value: 1000000\n" +
	"|-- --- Assignment\n" +
	"|-- --- --- Name: yen\n" +
	"|-- --- --- Operation: ASTERISK\n" +
	"|-- --- --- --- Name: dollars\n" +
	"|-- --- --- --- Value: 114\n" +
	"|-- --- Assignment\n" +
	"|-- --- --- Name: bitcoins\n" +
	"|-- --- --- Operation: FORWARD_SLASH\n" +
	"|-- --- --- --- Name: dollars\n" +
	"|-- --- --- --- Value: 1184\n";


	    assertEquals( expectedString, actualString);
	}

}
