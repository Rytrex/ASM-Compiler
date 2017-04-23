package syntaxtree;

/**
 * ArrayNodes hold an ExpressionNode and a String which is it's expression and name respectively
 * @author Don Vo
 */
public class ArrayNode extends VariableNode{

	/**Holds the ExpressionNode inside the square brackets*/
	private ExpressionNode node;

	/**
	 * Takes in a String that is set to the Array's name
	 * @param attr
	 */
	public ArrayNode(String attr) {
		super(attr);
	}

	/**
	 * Sets the Array's ExpressionNode
	 */
	public void setNode(ExpressionNode node){
		this.node = node;
	}

	/**
	 * Gets the Array's ExpresionNode
	 * @return ExpressionNode
	 */
	public ExpressionNode getNode(){
		return node;
	}

	/**
	 * Generates the Tree to the console for Arrays
	 * @param level int
	 * @return String
	 */
	@Override
    public String indentedToString( int level) {
        String answer = this.indentation(level);
        answer += "Name: " + this.name + "\n";
        answer += "Expression: " + this.node + "\n";
        return answer;
    }
}
