package syntaxtree;

/**
 * The WhileStatementNode class holds an ExpressionNode that checks if the loop continues and a
 * StatementNode which is what the program does if the loop continues
 * @author Don Vo
 */
public class WhileStatementNode extends StatementNode {

	/**Holds the case that will continue the while loop*/
	private ExpressionNode test;
	/**Holds what happens during the loop*/
	private StatementNode doStatement;

	/**
	 * Gets the ExpressionNode
	 * @return ExpressionNode
	 */
	public ExpressionNode getTest() {
		return test;
	}

	/**
	 * Sets the ExpressionNode
	 * @param test ExpressionNode
	 */
	public void setTest(ExpressionNode test) {
		this.test = test;
	}

	/**
	 * Gets the StatementNode
	 * @return StatementNode
	 */
	public StatementNode getDoStatement() {
		return doStatement;
	}

	/**
	 * Sets the StatementNode
	 * @param doStatement StatementNode
	 */
	public void setDoStatement(StatementNode doStatement) {
		this.doStatement = doStatement;
	}

	/**
	 * Generates the Tree to the console for WhileStatements
	 * @param level int
	 * @return String
	 */
	@Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Assignment\n";
        answer += this.test.indentedToString( level + 1);
        answer += this.doStatement.indentedToString( level + 1);
        return answer;
    }

}
