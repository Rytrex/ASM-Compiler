package syntaxtree;

/**
 * ReadNode holds a variable node that would be read in the Assembly code
 * @author Don Vo
 */
public class ReadNode extends StatementNode{
    private VariableNode var;

    /**
     * Initializes the ReadNode and takes in a VariableNode
     * @param node VariableNode
     */
    public ReadNode(VariableNode node){
        var = node;
    }

    /**
     * Gets the VariableNode
     * @return VariableNode
     */
    public VariableNode getVar(){
        return var;
    }

    /**
     * Generates the Tree to the console for ReadNode
     * @param level int
     * @return String
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation(level);
        answer += "Read\n";
        answer += var.indentedToString(level+1);
        return answer;
    }
}
