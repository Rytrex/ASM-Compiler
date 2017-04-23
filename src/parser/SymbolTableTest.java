package parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SymbolTableTest {

	SymbolTable sym = new SymbolTable();

	@Test
	public void test() {
		sym.add("list", Kind.ARRAY, "REAL", 0, 1);
		sym.add("doStuff", Kind.FUNCTION, "INTEGER", null, null);
		sym.add("prog", Kind.PROGRAM, "", null, null);
		sym.add("foo", Kind.VAR, "REAL", null, null);
		sym.add("prod", Kind.PROCEDURE, null, null, null);
		assertEquals(sym.isArrayName("list"), true);
		assertEquals(sym.isArrayName("doStuff"), false);
		assertEquals(sym.isFunctionName("doStuff"), true);
		assertEquals(sym.isFunctionName("list"), false);
		assertEquals(sym.isProgramName("prog"), true);
		assertEquals(sym.isProgramName("list"), false);
		assertEquals(sym.isVariableName("foo"), true);
		assertEquals(sym.isVariableName("list"), false);
		assertEquals(sym.isProcedureName("prod"), true);
		assertEquals(sym.isProcedureName("list"), false);
	}
}
