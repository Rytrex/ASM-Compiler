
package syntaxtree;

import scanner.TokenType;

/**
 * Represents a variable in the syntax tree.
 * @author Erik Steinmetz
 */
public class VariableNode extends ExpressionNode {

	/** The name of the variable associated with this node. */
	String name;
	TokenType type;

	/**
	 * Creates a ValueNode with the given attribute.
	 * @param attr The attribute for this value node.
	 */
	public VariableNode( String attr) {
		this.name = attr;
	}

	/**
	 * Creates a ValueNode with the given attribute.
	 * @param attr The attribute for this value node.
	 * @param type is the type integer or real
	 */
	public VariableNode( String attr, TokenType type) {
		this.name = attr;
		this.type = type;
	}
	/** 
	 * Returns the name of the variable of this node.
	 * @return The name of this VariableNode.
	 */
	public String getName() { return( this.name);}



	/**
	 * Returns the name of the variable as the description of this node.
	 * @return The attribute String of this node.
	 */
	@Override
	public String toString() {
		return( name);
	}

	/**
	 * Generates the Tree to the console for VariableNode
	 * @param level int
	 * @return String
	 */
	@Override
	public String indentedToString( int level) {
		String answer = this.indentation(level);
		if(type != null) {
			answer += "Name: " + this.name + " (" + this.type +  ")\n";
		}
		else{
			answer += "Name: " + this.name + "\n";
		}
		return answer;
	}

	/**
	 * Checks to see if the passed in object is equal to the VariableNode
	 * @param o object
	 * @return boolean, true if equal, else false
	 */
	@Override
	public boolean equals( Object o) {
		boolean answer = false;
		if( o instanceof VariableNode) {
			VariableNode other = (VariableNode)o;
			if( this.name.equals( other.name)) answer = true;
		}
		return answer;
	}    

}
