package scanner;
import java.util.HashMap;

/**
 * The LookUpTable class identifies if the next word in the file read by the scanner is any keywords.
 * @author Don Vo
 */
public class LookUpTable extends HashMap<String, TokenType>{
	/**
	 * LookUpTable is used to match keywords to their respective TokenType
	 */
	public LookUpTable(){
		this.put("and", TokenType.AND);
		this.put("array", TokenType.ARRAY);
		this.put("begin", TokenType.BEGIN);
		this.put("div", TokenType.DIV);
		this.put("do", TokenType.DO);
		this.put("else", TokenType.ELSE);
		this.put("end", TokenType.END);
		this.put("function", TokenType.FUNCTION);
		this.put("if", TokenType.IF);
		this.put("integer", TokenType.INTEGER);
		this.put("mod", TokenType.MOD);
		this.put("not", TokenType.NOT);
		this.put("of", TokenType.OF);
		this.put("or", TokenType.OR);
		this.put("procedure", TokenType.PROCEDURE);
		this.put("program", TokenType.PROGRAM);
		this.put("real", TokenType.REAL);
		this.put("then", TokenType.THEN);
		this.put("var", TokenType.VAR);
		this.put("while", TokenType.WHILE);
		this.put(";", TokenType.SEMI_COLON);
		this.put(",", TokenType.COMMA);
		this.put(".", TokenType.PERIOD);
		this.put(":", TokenType.COLON);
		this.put("[", TokenType.LEFT_SQUARE_BRACKET);
		this.put("]", TokenType.RIGHT_SQUARE_BRACKET);
		this.put("(", TokenType.LEFT_PARENTHESIS);
		this.put(")", TokenType.RIGHT_PARENTHESIS);
		this.put("+", TokenType.PLUS);
		this.put("-", TokenType.MINUS);
		this.put("=", TokenType.EQUALS);
		this.put("<>", TokenType.LESS_THAN_GREATER_THAN);
		this.put("<", TokenType.LESS_THAN);
		this.put("<=", TokenType.LESS_THAN_EQUALS_TO);
		this.put(">", TokenType.GREATER_THAN);
		this.put(">=", TokenType.GREATER_THAN_EQUAL_TO);
		this.put("*", TokenType.ASTERISK);
		this.put("/", TokenType.FORWARD_SLASH);
		this.put(":=", TokenType.COLON_EQUALS_TO);
		
	}
}
