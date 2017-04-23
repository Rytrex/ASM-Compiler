package parser;

import scanner.TokenType;
import java.util.HashMap;

/**
 * The Symbol class is used to hold the identifiers for FUNCTION, VAR, PROGRAM, ARRAY, PROCEDURE, and ARGUMENT
 * @author Don Vo
 */
public class Symbol {
    /**Holds the Identifier*/
    private String id;
    /**Holds the kind*/
    private Kind kind;
    /**Holds the type if it is an array*/
    private String type;
    /**Holds the Start and End positions if it is an array*/
    private Integer start, end;
    /**Holds the memory location for the symbol*/
    private String memoryLocation;
    private HashMap<String, Symbol> localTable = null;

    /**
     * Creates a symbol that takes in the identifier, TokenType, and start and end positions if Array
     * @param id String, Identifier
     * @param kind Kind, TokenType clone
     * @param type String, INTEGER or REAL for values
     * @param start Integer, Start position for arrays
     * @param end Integer, End position for arrays
     */
    public Symbol(String id, Kind kind, String type, Integer start, Integer end){
        this.id = id;
        this.kind = kind;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the identifier string
     * @return String, identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the TokenType clone, kind
     * @return Kind, TokenType clone
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * Returns the String type if variable
     * @return INTEGER or REAL as a String
     */
    public String getType(){
        return type;
    }

    /**
     * Returns the Start position integer if array
     * @return Integer, start position
     */
    public Integer getStart(){
        return start;
    }

    /**
     * Returns the End position integer if array
     * @return Integer, end position
     */
    public Integer getEnd(){
        return end;
    }

    /**
     * Returns the memoryLocation String
     * @return String, memoryLocation
     */
    public String getMemoryLocation(){return memoryLocation;}

    /**
     * Sets the memoryLocation String
     * @param in String, memoryLocation
     */
    public void setMemoryLocation(String in){memoryLocation = in;}

    /**
     * Returns the TokenType equivalent of Kind
     * @return TokenType, based on kind
     */
    public TokenType getTokenType(){
        if(kind.equals(Kind.FUNCTION)){
            return TokenType.FUNCTION;
        }
        else if(kind.equals(Kind.VAR)){
            return TokenType.VAR;
        }
        else if(kind.equals(Kind.PROGRAM)){
            return TokenType.PROGRAM;
        }
        else if(kind.equals(Kind.ARRAY)){
            return TokenType.ARRAY;
        }
        else if(kind.equals(Kind.FUNCTION)){
            return TokenType.FUNCTION;
        }
        return null;
    }

    /**
     * Returns the localTable where the symbol is stored
     * @return HashMap<String, Symbol>, localTable
     */
    public HashMap<String, Symbol> getLocalTable() {return localTable;}

    /**
     * Sets the localTable where the symbol is stored
     * @param localTable, HashMap<String, Symbol>
     */
    public void setLocalTable(HashMap<String, Symbol> localTable) {this.localTable = localTable;}

    /**
     * Generates a string with the information of the Symbol
     * @return String
     */
    public String toString(){
        return id + ": " + kind.toString() + ", " + type;
    }
}
enum Kind{
    FUNCTION, VAR, PROGRAM, ARRAY, PROCEDURE
}