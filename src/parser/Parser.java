package parser;
import scanner.MyScanner;
import scanner.Token;
import scanner.TokenType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import syntaxtree.*;

/**
 * The Parser class reads the file passed in and creates a Syntax Tree based on the
 * text of the file passed in. The Syntax Tree is passed into the Code Generator to
 * create MIPS Assembly Code
 * @author Don Vo
 */
public class Parser {

	private Token lookahead;
	private MyScanner scanner;
	private SymbolTable symTable;

	/**
	 * Sets up parser to read the inputted string. If the string is a file, the boolean is true,
	 * otherwise the string is the code and boolean is false
	 * @param filename
	 * @param file
	 */
	public Parser(String filename, boolean file) {
		FileInputStream fis = null;
		if(file){
			try {
				fis = new FileInputStream(filename);
			} catch (FileNotFoundException ex) {
				error("No file");
			}
			InputStreamReader isr = new InputStreamReader(fis);
			scanner = new MyScanner(isr);
		}
		else{
			scanner = new MyScanner(new StringReader(filename));
		}

		try {
			lookahead = scanner.nextToken();
		} catch (IOException ex) {
			error("Scan error");
		}
		symTable = new SymbolTable();
	}

	/**program -> program id ;
	 * 			 declarations()
	 * 			 subprogram_declarations()
	 * 			 compound_statement()
	 * 			 .
	 */
	public ProgramNode program(){
		match(TokenType.PROGRAM);
		symTable.add(lookahead.getLexeme(), Kind.PROGRAM, "", null, null);
		ProgramNode node = new ProgramNode(lookahead.getLexeme());
		match(TokenType.ID);
		match(TokenType.SEMI_COLON);
		node.setVariables(declarations());
		node.setFunctions(subprogram_declarations());
		node.setMain(compound_statement());
		match(TokenType.PERIOD);
		return node;
	}
	/**identifier_list -> id
	 * 				  -> id ,
	 * 					 identifier_list()
	 */
	public ArrayList<String> identifier_list(){
		ArrayList<String> list = new ArrayList<String>();
		list.add(lookahead.getLexeme());
		match(TokenType.ID);
		if(lookahead.getType() == TokenType.COMMA){
			match(TokenType.COMMA);
			list.addAll(identifier_list());
		}
		return list;
	}
	/**declarations -> var
	 * 				  identifier_list()
	 * 				  :
	 * 				  type()
	 * 				  ;
	 * 				  declarations()
	 * 			   -> LAMBDA
	 */
	public DeclarationsNode declarations(){
		DeclarationsNode node = new DeclarationsNode();
		if(lookahead.getType() == TokenType.VAR){
			match(TokenType.VAR);
			ArrayList<String> idList = identifier_list();
			match(TokenType.COLON);
			ArrayList<VariableNode> vNodes = type(idList);
			for (VariableNode vNode : vNodes){
				node.addVariable(vNode);
				
			}
			match(TokenType.SEMI_COLON);
			node.addVariable(declarations());
		}
		//else lambda
		return node;
	}
	/**type -> standard_type()
	 *     -> array [ num : num ] of
	 *     	  standard_type()
	 */
	public ArrayList<VariableNode> type(ArrayList<String> idList){
		Integer start = null, end = null;
		String type = null;
		Kind kind = null;
		ArrayList<VariableNode> list = new ArrayList<VariableNode>();
		if(lookahead.getType() == TokenType.ARRAY){
			match(TokenType.ARRAY);
			match(TokenType.LEFT_SQUARE_BRACKET);
			start = new Integer(lookahead.getLexeme());
			match(TokenType.NUMBER);
			match(TokenType.COLON);
			end = new Integer(lookahead.getLexeme());
			match(TokenType.NUMBER);
			match(TokenType.RIGHT_SQUARE_BRACKET);
			match(TokenType.OF);
			type = standard_type();
			kind = Kind.ARRAY;
		}
		else if(lookahead.getType() == TokenType.INTEGER
				|| lookahead.getType() == TokenType.REAL){
			type = standard_type();
			kind = Kind.VAR;
		}
		for(int i=0; i<idList.size(); i++){
			boolean added = symTable.add(idList.get(i), kind, type, start, end);
			if(added && (kind == Kind.ARRAY || kind == Kind.VAR)){
				TokenType passType;
				if(type.equals("INTEGER")){
					passType = TokenType.INTEGER;
				}
				else{
					passType = TokenType.VAR;
				}
				list.add(new VariableNode(idList.get(i), passType));
			}
			else{
				error("type, failed to add");
			}
		}
		return list;

	}
	/**standard_type -> integer
	 * 				-> real 
	 */
	public String standard_type(){
		if(lookahead.getType() == TokenType.INTEGER){
			match(TokenType.INTEGER);
			return "INTEGER";
		}
		else if(lookahead.getType() == TokenType.REAL){
			match(TokenType.REAL);
			return "REAL";
		}
		else {
			error("standard_type");
			return null;
		}
	}
	/**subprogram_declarations -> subprogram_declaration()
	 * 							 ;
	 * 							 subprogram_declarations()
	 * 						  -> LAMBDA
	 */
	public SubProgramDeclarationsNode subprogram_declarations(){
		SubProgramDeclarationsNode node = new SubProgramDeclarationsNode();
		if(lookahead.getType() == TokenType.FUNCTION
				|| lookahead.getType() == TokenType.PROCEDURE){
			node.addSubProgramDeclaration(subprogram_declaration());
			match(TokenType.SEMI_COLON);
			node.addSubProgramDeclaration(subprogram_declarations());
		}
		//else lambda
		return node;
	}
	/**subprogram_declaration -> subprogram_head()
	 * 							declarations()
	 * 							subprogram_declarations()
	 * 							compound_statement() 
	 */
	public SubProgramNode subprogram_declaration(){
		SubProgramNode node = subprogram_head();
		node.setVariables(declarations());
		node.setFunctions(subprogram_declarations());
		node.setMain(compound_statement());
		return node;
	}
	/**subprogram_head -> function id
	 * 					 arguments()
	 * 					 :
	 * 					 standard_type()
	 * 					 ;
	 * 				  -> procedure id
	 * 					 arguments
	 * 					 ; 
	 */
	public SubProgramNode subprogram_head(){
		SubProgramNode node = null;
		String id = "";
		if(lookahead.getType() == TokenType.FUNCTION){
			match(TokenType.FUNCTION);
			node = new SubProgramNode(lookahead.getLexeme());
			id = lookahead.getLexeme();
			match(TokenType.ID);
			node.setArguments(arguments());
			match(TokenType.COLON);
			symTable.add(id, Kind.FUNCTION, standard_type(), null, null);
			match(TokenType.SEMI_COLON);
		}
		else if(lookahead.getType() == TokenType.PROCEDURE){
			match(TokenType.PROCEDURE);
			node = new SubProgramNode(lookahead.getLexeme());
			symTable.add(lookahead.getLexeme(), Kind.PROCEDURE, null, null, null);
			match(TokenType.ID);
			node.setArguments(arguments());
			match(TokenType.SEMI_COLON);
		}
		else{
			error("subprogram_head");
		}
		return node;
	}
	/**arguments -> (
	 * 			   parameter_list()
	 * 			   )
	 * 			-> LAMBDA 
	 */
	public ArrayList<VariableNode> arguments(){
		ArrayList<VariableNode> list = null;
		if(lookahead.getType() == TokenType.LEFT_PARENTHESIS){
			match(TokenType.LEFT_PARENTHESIS);
			list = parameter_list();
			match(TokenType.RIGHT_PARENTHESIS);
		}
		//else lambda
		return list;
	}
	/**parameter_list -> identifier_list()
	 * 					:
	 * 					type()
	 * 				 -> identifier_list()
	 * 					:
	 * 					type()
	 * 					;
	 * 					parameter_list()
	 */
	public ArrayList<VariableNode> parameter_list(){
		ArrayList<String> idList = identifier_list();
		match(TokenType.COLON);
		ArrayList<VariableNode> list = type(idList);
		if(lookahead.getType() == TokenType.SEMI_COLON){
			match(TokenType.SEMI_COLON);
			list.addAll(parameter_list());
		}
		return list;
	}
	/**compound_statement -> begin
	 * 						optional_statements()
	 * 						end
	 */
	public CompoundStatementNode compound_statement(){
		CompoundStatementNode node = new CompoundStatementNode();
		match(TokenType.BEGIN);
		node.addStatement(optional_statements());
		match(TokenType.END);
		return node;
	}
	/**optional_statements -> statement_list()
	 * 					  -> LAMBDA
	 */
	public ArrayList<StatementNode> optional_statements(){
		ArrayList<StatementNode> list = null;
		if(lookahead.getType() == TokenType.ID 
				|| lookahead.getType() == TokenType.BEGIN
				|| lookahead.getType() == TokenType.IF
				|| lookahead.getType() == TokenType.WHILE
				|| (symTable.isFunctionName(lookahead.getLexeme())
						&& (lookahead.getLexeme().equals("read") 
								|| lookahead.getLexeme().equals("write")))
				){
			list = statement_list();
		}
		//else lambda
		return list;
	}
	/**statement_list -> statement()
	 * 				 -> statement()
	 * 					;
	 * 					statement_list()
	 */
	public ArrayList<StatementNode> statement_list(){
		ArrayList<StatementNode> list = new ArrayList<StatementNode>();
		list.add(statement());
		if(lookahead.getType() == TokenType.SEMI_COLON){
			match(TokenType.SEMI_COLON);
			list.addAll(statement_list());
		}
		return list;
	}
	/**statement -> variable()
	 * 			   assignop
	 * 			   expression
	 * 			-> procedure_statement()
	 * 			-> compound_statement()
	 * 			-> if
	 * 			   expression()
	 * 			   then
	 * 			   statement()
	 * 			   else
	 * 			   statement()
	 * 			-> while
	 * 			   expression
	 * 			   do
	 * 			   statement()
	 * 			-> read( id )
	 * 			-> write( expression )
	 */
	public StatementNode statement(){
		if(lookahead.getType() == TokenType.IF){
			IfStatementNode ifNode = new IfStatementNode();
			match(TokenType.IF);
			ifNode.setTest(expression());
			match(TokenType.THEN);
			ifNode.setThenStatement(statement());
			match(TokenType.ELSE);
			ifNode.setElseStatement(statement());
			return ifNode;
		}
		else if(lookahead.getType() == TokenType.WHILE){
			WhileStatementNode whileNode = new WhileStatementNode();
			match(TokenType.WHILE);
			whileNode.setTest(expression());
			match(TokenType.DO);
			whileNode.setDoStatement(statement());
			return whileNode;
		}
		else if(lookahead.getType() == TokenType.BEGIN){
			return compound_statement();
		}
		else if(lookahead.getType() == TokenType.ID){
			if(symTable.isProcedureName(lookahead.getLexeme())){
				WriteNode node = new WriteNode();
				match(TokenType.ID);
				match(TokenType.LEFT_PARENTHESIS);
				node.setExpression(expression());
				match(TokenType.RIGHT_PARENTHESIS);
				return node;
			}
			else{
				String name = lookahead.getLexeme();
				match(TokenType.ID);
				VariableNode node = variable(name);
				if(lookahead.getType() == TokenType.COLON_EQUALS_TO){
					AssignmentStatementNode aNode = new AssignmentStatementNode();
					aNode.setLvalue(node);
					match(TokenType.COLON_EQUALS_TO);
					aNode.setExpression(expression());
					return aNode;
				}
			}
		}
		else if(symTable.isFunctionName(lookahead.getLexeme())){
			if(lookahead.getLexeme().equals("read")){
				
			}
			else if(lookahead.getLexeme().equals("write"))
			match(TokenType.ID);
			match(TokenType.LEFT_PARENTHESIS);
			match(TokenType.ID);
			match(TokenType.RIGHT_PARENTHESIS);
		}
		return null; 
	}
	/**variable -> id
	 * 		   -> id [
	 * 			  expression()
	 * 			  ]
	 */
	public VariableNode variable(String name){
		VariableNode node = new VariableNode(name);
		//TokenType.ID is matched in statement()
		if(lookahead.getType() == TokenType.LEFT_SQUARE_BRACKET){
			ArrayNode aNode = new ArrayNode(node.getName());
			match(TokenType.LEFT_SQUARE_BRACKET);
			aNode.setNode(expression());
			match(TokenType.RIGHT_SQUARE_BRACKET);
			return aNode;
		}
		return node;
	}
	/**procedure_statement -> id
	 * 					  -> id (
	 * 						 expression_list()
	 * 						 ) 
	 */
	public ArrayList<ExpressionNode> procedure_statement(){
		//TokenType.ID is matched in statement()
		if(lookahead.getType() == TokenType.LEFT_PARENTHESIS){
			match(TokenType.LEFT_PARENTHESIS);
			ArrayList<ExpressionNode> list = expression_list();
			match(TokenType.RIGHT_PARENTHESIS);
			return list;
		}
		return null;
	}
	/**expressoin_list -> expression()
	 * 				  -> expression()
	 * 					 ,
	 * 					 expression_list() 
	 */
	public ArrayList<ExpressionNode> expression_list(){
		ArrayList<ExpressionNode> list = new ArrayList<ExpressionNode>();
		list.add(expression());
		if(lookahead.getType() == TokenType.COMMA){
			match(TokenType.COMMA);
			list.addAll(expression_list());
		}
		return list;
	}
	/**expression -> simple_expression()
	 * 			 -> simple_expression()
	 * 				relop
	 * 				simple_expression()
	 * 
	 */
	public ExpressionNode expression(){
		ExpressionNode left = simple_expression();
		if(isRelop()){
			OperationNode node = new OperationNode(matchRelop());
			node.setLeft(left);
			node.setRight(simple_expression());
			return node;
		}
		return left;
	}
	/**simple_expression -> term()
	 * 					   simple_part()
	 * 					-> sign()
	 * 					   term()
	 * 					   simple_part() 
	 */
	public ExpressionNode simple_expression(){
		UnaryOpNode node = null;
		if(lookahead.getType() == TokenType.PLUS 
				|| lookahead.getType() == TokenType.MINUS){
			node = new UnaryOpNode(sign());
		}
		if(node == null){
			ExpressionNode eNode = term();
			return simple_part(eNode);
		}
		else{
			node.setNode(term());
			return simple_part(node);
		}
	}
	/**simple_part -> addop
	 * 				 term()
	 * 				 simple_part()
	 * 			  -> LAMBDA 
	 */
	public ExpressionNode simple_part(ExpressionNode maybeLeft){
		if(isAddop()){
			OperationNode oNode = new OperationNode(matchAddop());
			oNode.setLeft(maybeLeft);
			ExpressionNode eNode = term();
			oNode.setRight(simple_part(eNode));
			return oNode;
		}
		//else lambda
		return maybeLeft;
	}
	/**term -> factor()
	 * 		  term_part() 
	 */
	public ExpressionNode term(){
		ExpressionNode node = factor();
		return term_part(node);
	}
	/**term_part -> mulop
	 * 			   factor()
	 * 			   term_part()
	 * 			-> LAMBDA 
	 */
	public ExpressionNode term_part(ExpressionNode maybeLeft){
		OperationNode node = null;
		if(isMulop()){
			node = new OperationNode(matchMulop());
			node.setLeft(maybeLeft);
			ExpressionNode eNode = factor();
			node.setRight(term_part(eNode));
			return node;
		}
		//else lambda
		return maybeLeft;
	}
	/**factor -> id
	 * 		 -> id [
	 * 			expression()
	 * 			]
	 * 		 -> id (
	 * 			expression()
	 * 			)
	 * 		 -> num
	 * 		 -> (
	 * 			expression
	 * 			)
	 * 		 -> not
	 * 			factor() 
	 */
	public ExpressionNode factor(){
		if(symTable.isVariableName(lookahead.getLexeme())
				|| symTable.isArrayName(lookahead.getLexeme())){
			VariableNode node = new VariableNode(lookahead.getLexeme());
			match(TokenType.ID);
			if(lookahead.getType() == TokenType.LEFT_SQUARE_BRACKET){
				ArrayNode aNode = new ArrayNode(node.getName());
				match(TokenType.LEFT_SQUARE_BRACKET);
				aNode.setNode(expression());
				match(TokenType.RIGHT_SQUARE_BRACKET);
				return node;
			}
			else if(lookahead.getType() == TokenType.LEFT_PARENTHESIS){
				ProcedureStatementNode pNode = new ProcedureStatementNode(node);
				match(TokenType.LEFT_PARENTHESIS);
				pNode.setList(expression_list());
				match(TokenType.RIGHT_PARENTHESIS);
				return node;
			}
			return node;
			//else nothing
		}
		else if(lookahead.getType() == TokenType.NUMBER){
			ValueNode node = new ValueNode(lookahead.getLexeme());
			match(TokenType.NUMBER);
			return node;
		}
		else if(lookahead.getType() == TokenType.LEFT_PARENTHESIS){
			match(TokenType.LEFT_PARENTHESIS);
			ExpressionNode node = expression();
			match(TokenType.RIGHT_PARENTHESIS);
			return node;
		}
		else if(lookahead.getType() == TokenType.NOT){
			UnaryOpNode node = new UnaryOpNode(TokenType.NOT);
			match(TokenType.NOT);
			node.setNode(factor());
			return node;
		}
		else{
			error("factor");
		}
		return null;
	}
	/**sign -> +
	 * 	   -> -
	 */
	public TokenType sign(){
		if(lookahead.getType() == TokenType.PLUS){
			match(TokenType.PLUS);
			return TokenType.PLUS;
		}
		else if(lookahead.getType() == TokenType.MINUS){
			match(TokenType.MINUS);
			return TokenType.MINUS;
		}
		else{
			error("sign");
		}
		return null;
	}
	/**
	 * Identifies if the next token is an Relop
	 * which is =, <>, <, <=, >=, or >
	 * @return true if relop, false if not
	 */
	private boolean isRelop(){
		if(lookahead.getType() == TokenType.EQUALS
				|| lookahead.getType() == TokenType.LESS_THAN_GREATER_THAN
				|| lookahead.getType() == TokenType.LESS_THAN
				|| lookahead.getType() == TokenType.LESS_THAN_EQUALS_TO
				|| lookahead.getType() == TokenType.GREATER_THAN_EQUAL_TO
				|| lookahead.getType() == TokenType.GREATER_THAN){
			return true;
		}
		return false;
	}
	/**
	 * Matches the correct Relop if the isRelop method
	 * returns true
	 * @return TokenType of the next lexeme
	 */
	private TokenType matchRelop(){
		if(lookahead.getType() == TokenType.EQUALS){
			match(TokenType.EQUALS);
			return TokenType.EQUALS;
		}
		else if(lookahead.getType() == TokenType.LESS_THAN_GREATER_THAN){
			match(TokenType.LESS_THAN_GREATER_THAN);
			return TokenType.LESS_THAN_GREATER_THAN;
		}
		else if(lookahead.getType() == TokenType.LESS_THAN){
			match(TokenType.LESS_THAN);
			return TokenType.LESS_THAN;
		}
		else if(lookahead.getType() == TokenType.LESS_THAN_EQUALS_TO){
			match(TokenType.LESS_THAN_EQUALS_TO);
			return TokenType.LESS_THAN_EQUALS_TO;
		}
		else if(lookahead.getType() == TokenType.GREATER_THAN_EQUAL_TO){
			match(TokenType.GREATER_THAN_EQUAL_TO);
			return TokenType.GREATER_THAN_EQUAL_TO;
		}
		else{
			match(TokenType.GREATER_THAN);
			return TokenType.GREATER_THAN;
		}
	}
	/**
	 * Identifies if the next token is an Relop
	 * which is *, /, DIV, MOD, or AND
	 * @return true if mulop, false if not
	 */
	private boolean isMulop(){
		if(lookahead.getType() == TokenType.ASTERISK
				|| lookahead.getType() == TokenType.FORWARD_SLASH
				|| lookahead.getType() == TokenType.DIV
				|| lookahead.getType() == TokenType.MOD
				|| lookahead.getType() == TokenType.AND){
			return true;
		}
		return false;
	}
	/**
	 * Matches the correct mulop if the isMulop method
	 * returns true
	 * @return TokenType of the next lexeme
	 */
	private TokenType matchMulop(){
		if(lookahead.getType() == TokenType.ASTERISK){
			match(TokenType.ASTERISK);
			return TokenType.ASTERISK;
		}
		else if(lookahead.getType() == TokenType.FORWARD_SLASH){
			match(TokenType.FORWARD_SLASH);
			return TokenType.FORWARD_SLASH;
		}
		else if(lookahead.getType() == TokenType.DIV){
			match(TokenType.DIV);
			return TokenType.DIV;
		}
		else if(lookahead.getType() == TokenType.MOD){
			match(TokenType.MOD);
			return TokenType.MOD;
		}
		else{
			match(TokenType.AND);
			return TokenType.AND;
		}
	}
	/**
	 * Identifies if the next token is an Relop
	 * which is + or -
	 * @return true if addop, false if not
	 */
	private boolean isAddop(){
		if(lookahead.getType() == TokenType.PLUS
				|| lookahead.getType() == TokenType.MINUS
				|| lookahead.getType() == TokenType.OR){
			return true;
		}
		return false;
	}
	/**
	 * Matches the correct addop if the isAddop method
	 * returns true
	 * @return TokenType of the next lexeme
	 */
	private TokenType matchAddop(){
		if(lookahead.getType() == TokenType.PLUS){
			match(TokenType.PLUS);
			return TokenType.PLUS;
		}
		else if(lookahead.getType() == TokenType.MINUS){
			match(TokenType.MINUS);
			return TokenType.MINUS;
		}
		else{
			match(TokenType.OR);
			return TokenType.OR;
		}
	}
	/**
	 * Matches the expected token.
	 * If the current token in the input stream from the scanner
	 * matches the token that is expected, the current token is
	 * consumed and the scanner will move on to the next token
	 * in the input.
	 * The null at the end of the file returned by the
	 * scanner is replaced with a fake token containing no
	 * type.
	 * @param expected The expected token type.
	 */
	public void match(TokenType expected) {
		System.out.println("match\t" + expected + "\t\t\t" + lookahead.getLexeme());
		if(this.lookahead.getType() == expected) {
			try {
				this.lookahead = scanner.nextToken();
				if( this.lookahead == null) {
					this.lookahead = new Token( "End of File", null);
				}
			} catch (IOException ex) {
				error( "Scanner exception");
			}
		}
		else {
			error("Match of " + expected + " found " + this.lookahead.getType()
			+ " instead.");
		}
	}
	/**
	 * Errors out of the parser.
	 * Prints an error message and then exits the program.
	 * @param message The error message to print.
	 */
	public void error(String message) {
		System.out.println("Error " + message);
		System.exit(1);
	}

	public SymbolTable getSymTable(){return symTable;}
}
