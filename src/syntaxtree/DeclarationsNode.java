
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a set of declarations in a Pascal program.
 * @author Don Vo based off of Erik Steinmetz
 */
public class DeclarationsNode extends SyntaxTreeNode {
    
	/**Holds the list of VariableNodes*/
    private ArrayList<VariableNode> vars = new ArrayList<VariableNode>();
    
    /**
     * Adds a VariableNode into the list.
     * @param aVariable, new variable node to be added
     */
    public void addVariable( VariableNode aVariable) {
        vars.add( aVariable);
    }
    /**
     * Adds a list of VariableNodes to the list.
     * @param aVariable
     */
    public void addVariable( DeclarationsNode aVariable) {
        vars.addAll( aVariable.vars);
    }
    /**
     * Generates the Tree to the console for Declarations
     * @param level int
     * @return String
     */
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer+="Declarations\n";
        for( VariableNode variable : vars) {
            answer += variable.indentedToString( level + 1);
        }
        return answer;
    }

    /**
     * Returns the list of VariableNodes
     * @return ArrayList<VaraibleNode>
     */
    public ArrayList<VariableNode> getVars(){
        return vars;
    }
}
