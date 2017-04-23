package syntaxtree;

import scanner.TokenType;

/**
 * Represents an operation of [sign] or [not] in an expression.
 * @author Don Vo
 */

public class UnaryOpNode extends ExpressionNode{

	/** The right operator of this operation. */
	private ExpressionNode node;

	/** The kind of operation. */
	private TokenType operation;

	/**
	 * Creates an operation node given an operation token.
	 * @param op The token representing this node's unary operation.
	 */
	public UnaryOpNode ( TokenType op) {
		this.operation = op;
	}


	/**
	 * Gets the ExpressionNode
	 * @return ExpressionNode
	 */
	public ExpressionNode getNode() { return( this.node);}
	public TokenType getOperation() { return( this.operation);}

	/**
	 * Sets the ExpressionNode
	 * @param node ExpressionNode
	 */
	public void setNode( ExpressionNode node) { this.node = node;}
	public void setOperation( TokenType op) { this.operation = op;}

	/**
	 * Returns the operation token as a String.
	 * @return The String version of the operation token.
	 */
	@Override
	public String toString() {
		return operation.toString();
	}

	/**
	 * Generates the Tree to the console for UnaryOp
	 * @param level int
	 * @return String
	 */
	@Override
	public String indentedToString( int level) {
		String answer = this.indentation(level);
		answer += "Operation: " + this.operation + "\n";
		answer += node.indentedToString(level + 1);
		return( answer);
	}

	/**
	 * Checks to see if the passed in object is equal to the UnaryOpNode
	 * @param o object
	 * @return boolean, true if equal, else false
	 */
	@Override
	public boolean equals( Object o) {
		boolean answer = false;
		if( o instanceof UnaryOpNode) {
			UnaryOpNode other = (UnaryOpNode)o;
			if( (this.operation == other.operation) &&
					this.node.equals( other.node)) answer = true;
		}
		return answer;
	}
}
