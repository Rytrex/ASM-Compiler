package scanner;
/**
 *  A simple Token class containing only a String.
 *  @author Don Vo based off of Erik Steinmetz
 */
public class Token
{
	//The word of the token
    private String contents;
    //The assigned token type
    private TokenType type;

    /**
     * Creates a token with the Token's name and type
     * @param input String, name
     * @param inType TokenType
     */
    public Token(String input, TokenType inType)
    {
        this.contents = input;
        this.type = inType;
    }

    /**
     * Returns the name of the Token
     * @return String, name
     */
    public String getLexeme() { 
    	return this.contents;
    }

    /**
     * Returns the TokenType
     * @return TokenType
     */
    public TokenType getType(){
    	return this.type;
    }

    /**
     * Returns the Token as a String
     * @return String
     */
    @Override
    public String toString() { 
    	return "[Token: " + this.contents + " Type: " + this.type + "]";
    }
}