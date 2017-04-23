package parser;

import scanner.TokenType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

/**
 * The Symbol Table holds all Symbols which hold all identifiers and their types.
 * @author Don Vo
 */
public class SymbolTable {
	private Stack<HashMap<String, Symbol>> stack = new Stack<HashMap<String, Symbol>>();
	/**
	 * Constructor for creating a SymbolTable and adds read and write methods to the table
	 */
	public SymbolTable(){
		stack.push(new HashMap<String,Symbol>());
		add("write", Kind.PROCEDURE, "VOID", null, null);
		add("read", Kind.PROCEDURE, "VOID", null, null);
	}
	/**
	 * Adds a new table to the stack
	 */
	public void addTable(){
		stack.push(new HashMap<String,Symbol>());
	}
	/**
	 * Adds the table that is passed in
	 * @param table, HashMap<String, Symbol>
	 */
	public void addTable(HashMap<String,Symbol> table){
		stack.push(table);
	}
	/**
	 * Removes the top table from the stack
	 * @return HashMap<String, Symbol>
	 */
	public HashMap<String, Symbol> removeTable(){
		if(stack.size()>1) return stack.pop();
		return null;
	}
	/** add(String, Kind)
	 * 	@param id, kind
	 * 	Used to add a new symbol into the HashMap containing the id and kind
	 */
	public boolean add(String id, Kind kind, String type, Integer start, Integer end){
		if(!stack.peek().containsKey(id)){
			stack.peek().put(id, new Symbol(id, kind, type, start, end));
			return true;
		}
		return false;
	}
	/** isVariableName(String)
	 *  Used to detect if the inputed string is a VAR
	 * @param id
	 * @return true if is in HashMap and is a VAR
	 * 		else false
	 */
	public boolean isVariableName(String id){
		for(int i = stack.size()-1; i>-1; i--){
			HashMap<String, Symbol> table = stack.elementAt(i);
			if(stack.peek().containsKey(id) && stack.peek().get(id).getKind() == Kind.VAR){
				return true;
			}
		}

		return false;
	}
	/** isFunctionName(String)
	 *  Used to detect if the inputed string is a FUNCTION
	 * @param id
	 * @return true if is in HashMap and is a FUNCTION
	 * 		else false
	 */
	public boolean isFunctionName(String id){
		for(int i = stack.size()-1; i>-1; i--){
			HashMap<String, Symbol> table = stack.elementAt(i);
			if(stack.peek().containsKey(id) && stack.peek().get(id).getKind() == Kind.FUNCTION){
				return true;
			}
		}
		return false;
	}
	/** isProgramName(String)
	 *  Used to detect if the inputed string is a PROGRAM
	 * @param id
	 * @return true if is in HashMap and is a PROGRAM
	 * 		else false
	 */
	public boolean isProgramName(String id){
		if(stack.firstElement().containsKey(id) && stack.firstElement().get(id).getKind() == Kind.PROGRAM){
			return true;
		}
		return false;
	}
	/** isArrayName(String)
	 *  Used to detect if the inputed string is a ARRAY
	 * @param id
	 * @return true if is in HashMap and is a ARRAY
	 * 		else false
	 */
	public boolean isArrayName(String id){
		for(int i = stack.size()-1; i>-1; i--){
			HashMap<String, Symbol> table = stack.elementAt(i);
			if(stack.peek().containsKey(id) && stack.peek().get(id).getKind() == Kind.ARRAY){
				return true;
			}
		}
		return false;
	}
	/** isProcedureName(String)
	 *  Used to detect if the inputed string is a PROCEDURE
	 * @param id
	 * @return true if is in HashMap and is a PROCEDURE
	 * 		else false
	 */
	public boolean isProcedureName(String id){
		for(int i = stack.size()-1; i>-1; i--){
			HashMap<String, Symbol> table = stack.elementAt(i);
			if(stack.peek().containsKey(id) && stack.peek().get(id).getKind() == Kind.PROCEDURE){
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns all of the Symbols in the top HashMap
	 * @return Collection<Symbol>
	 */
	public Collection<Symbol> getSymbols(){
		return stack.peek().values();
	}
	/**
	 * Returns the Symbol given the identifier
	 * @param id String, identifier
	 * @return Symbol
	 */
	public Symbol getSymbol(String id){
		for(int i = stack.size()-1; i>-1; i--){
			HashMap<String, Symbol> table = stack.elementAt(i);
			if(table.containsKey(id)){
				return table.get(id);
			}
		}
		return null;
	}
	/**
	 * Returns the type of the Symbol given the identifier
	 * @param id String, identifier
	 * @return TokenType
	 */
	public TokenType getReturnType(String id){
		return stack.peek().get(id).getTokenType();
	}
	/**
	 * Sets the local location of the Symbol given the identifier
	 * @param name String, identifier
	 * @param location String local location
	 */
	public void setLocation(String name, String location) {
		stack.peek().get(name).setMemoryLocation(location);
	}
	/**
	 * Gets the location of the Symbol given the identifier
	 * @param name String, identifier
	 * @return String, location
	 */
	public String getLocation(String name) {
		return stack.peek().get(name).getMemoryLocation();
	}

	/**
	 * If the identifier passed in is an array, returns the size of the array
	 * @param name String, identifer
	 * @return int, size
	 */
	public int getArrayLength(String name) {
		if (isArrayName(name)) {
			Symbol sym = stack.peek().get(name);
			return sym.getEnd() - sym.getStart();
		}
		return -1;
	}

	/**
	 * Gets the size of the top HashMap
	 * @return int, size
	 */
	public int getSize() {
		return stack.peek().size();
	}

	/**
	 * Sets the localTable of the Symbol based on the identifier passed in
	 * @param key String, identifier
	 * @param input HashMap<String, Symbol>, localTable
	 */
	public void setLocalTable(String key, HashMap<String,Symbol> input){
		if(stack.peek().containsKey(key)){stack.peek().get(key).setLocalTable(input);}
	}

	/**
	 * Gets the localTable of the identifier passed in
	 * @param key String, identifier
	 * @return HashMap<String, Symbol>
	 */
	public HashMap<String, Symbol> getLocalTable(String key){
		if(stack.peek().containsKey(key)){return stack.peek().get(key).getLocalTable();}
		return null;
	}
}
