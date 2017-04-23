
package syntaxtree;

/**
 * Represents an if statement in Pascal.
 * An if statement includes a boolean expression, and two statements.
 * @author Don Vo based off of Erik Steinmetz
 */
public class IfStatementNode extends StatementNode {
	/**Holds the ExpressionNode that will be tested*/
    private ExpressionNode test;
    /**Holds the Statement that will execute if the if expression is true*/
    private StatementNode thenStatement;
    /**Holds the Statement that executes if the if statement fails*/
    private StatementNode elseStatement;

    /**
     * Returns the test ExpressionNode
     * @return ExpressionNode
     */
    public ExpressionNode getTest() {
        return test;
    }

    /**
     * Sets the test ExpressionNode
     * @param test ExpressionNode
     */
    public void setTest(ExpressionNode test) {
        this.test = test;
    }
    /**
     * Returns the then StatementNode
     * @return StatementNode
     */
    public StatementNode getThenStatement() {
        return thenStatement;
    }

    /**
     * Sets the then StatementNode
     * @param thenStatement StatementNode
     */
    public void setThenStatement(StatementNode thenStatement) {
        this.thenStatement = thenStatement;
    }
    /**
     * Returns the else StatementNode
     * @return StatementNode
     */
    public StatementNode getElseStatement() {
        return elseStatement;
    }

    /**
     * Sets the else StatementNode
     * @param elseStatement StatementNode
     */
    public void setElseStatement(StatementNode elseStatement) {
        this.elseStatement = elseStatement;
    }

    /**
     * Generates the Tree to the console for IfStatements
     * @param level int
     * @return String
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "If\n";
        answer += this.test.indentedToString( level + 1);
        answer += this.thenStatement.indentedToString( level + 1);
        answer += this.elseStatement.indentedToString( level + 1);
        return answer;
    }

}
