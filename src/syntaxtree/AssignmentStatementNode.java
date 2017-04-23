
package syntaxtree;

/**
 * Represents a single assignment statement.
 * @author Don Vo based off of Erik Steinmetz
 */
public class AssignmentStatementNode extends StatementNode {
    
	/**Holds the VariableNode that will be assigned an expression*/
    private VariableNode lvalue;
    /**Holds the Expression that will be stored in a variable*/
    private ExpressionNode expression;

    /**
     * Returns the VariableNode for the left side of the assignment
     * @return VariableNode
     */
    public VariableNode getLvalue() {
        return lvalue;
    }
    /**
     * Gets the VariableNode for the left side of the assignment
     * @return VariableNode
     */
    public void setLvalue(VariableNode lvalue) {
        this.lvalue = lvalue;
    }
    /**
     * Returns the ExpressionNode for the right side of the assignment
     * @return ExpressionNode
     */
    public ExpressionNode getExpression() {
        return expression;
    }
    /**
     * Sets the ExpressionNode for the right side of the assignment
     * @return ExpressionNode
     */
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    /**
     * Generates the Tree to the console for AssignmentStatements
     * @param level int
     * @return String
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Assignment\n";
        answer += this.lvalue.indentedToString( level + 1);
        answer += this.expression.indentedToString( level + 1);
        return answer;
    }

    /**
     * Returns the AssignmentStatementNode in a String format
     * @return
     */
	@Override
	public String toString() {
		return "AssignmentStatementNode [lvalue=" + lvalue + ", expression=" + expression + "]";
	}
    
}
