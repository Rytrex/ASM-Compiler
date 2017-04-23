package syntaxtree;

import scanner.TokenType;
import java.util.ArrayList;

/**
 * FunctionNode holds a list of ExpressionNodes in the function and a String for the function's name
 */
public class FunctionNode extends VariableNode{
    private ArrayList<ExpressionNode> list;

    /**
     * Initializes the FunctionNode with a name and TokenType
     * @param attribute String, name
     * @param data TokenType
     */
    public FunctionNode(String attribute, TokenType data){
        super(attribute, null);
        list = new ArrayList<ExpressionNode>();
    }

    /**
     * Returns the name of the function
     * @return String, name
     */
    public String getName(){return( super.name);}

    /**
     * Returns the list of ExpressionNodes
     * @return ArrayList<ExpressioNodes>
     */
    public ArrayList<ExpressionNode> getList(){return list;}

    /**
     * Sets the list of ExpressionNodes
     * @param input ArrayList<ExpressionNode>
     */
    public void setList(ArrayList<ExpressionNode> input){list = input;}

    /**
     * Generates the Tree to the console for Functions
     * @param level int
     * @return String
     */
    @Override
    public String indentedToString( int level) {
        StringBuilder answer = new StringBuilder(this.indentation(level));
        answer.append("Name: ").append(super.name).append("\n");
        answer.append(this.indentation(level));
        answer.append("Arguments: \n");
        for( ExpressionNode expression : list) {
            answer.append(expression.indentedToString(level + 1));
        }
        return answer.toString();
    }

    /**
     * Checks if the passed in object is equal to the current FunctionNode
     * @param o Object
     * @return boolean, true if equal, else false
     */
    @Override
    public boolean equals( Object o) {
        boolean answer = false;
        if( o instanceof FunctionNode) {
            FunctionNode other = (FunctionNode)o;
            if( super.name.equals( other.getName()) && ( this.list.equals(other.getList())))
                answer = true;
        }
        return answer;
    }
}
