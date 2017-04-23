package syntaxtree;

/**
 * WriteNode holds an ExpressionNode for printing out numbers in Assembly
 * @author Don Vo
 */
public class WriteNode extends StatementNode{
    private ExpressionNode expression;

    /**
     * Sets the ExpressionNode
     * @param node ExpressionNode
     */
    public void setExpression(ExpressionNode node){
        expression = node;
    }

    /**
     * Gets the ExpressionNode
     * @return
     */
    public ExpressionNode getExpression(){
        return expression;
    }

    /**
     * Generates the Tree to the console for WriteNode
     * @param level int
     * @return String
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation(level);
        answer += "Write\n";
        answer += this.expression.indentedToString(level +1);
        return answer;
    }
}
