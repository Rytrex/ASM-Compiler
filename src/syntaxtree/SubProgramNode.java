
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a Pascal Program
 * @author Don Vo based off of Erik Steinmetz
 */
public class SubProgramNode extends SyntaxTreeNode {

    /**Holds the program's name*/
    private String name;
    /**Holds the declared variables*/
    private DeclarationsNode variables;
    /**Holds the SubPrograms*/
    private SubProgramDeclarationsNode functions;
    /**Holds the main function of the program*/
    private CompoundStatementNode main;
    private ArrayList<VariableNode> list;

    /**
     * Initializes the ProgramNode with a String name
     * @param aName String, name
     */
    public SubProgramNode( String aName) {
        this.name = aName;
    }

    /**
     * Gets the DeclarationsNode
     * @return DeclarationsNode
     */
    public DeclarationsNode getVariables() {
        return variables;
    }

    /**
     * Sets the DeclarationsNode
     * @param variables DeclarationsNode
     */
    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;
    }

    /**
     * Gets the SubProgramDeclarationsNode
     * @return SubProgramDeclarationsNode
     */
    public SubProgramDeclarationsNode getFunctions() {
        return functions;
    }

    /**
     * Sets the SubProgramDeclarationsNode
     * @param functions
     */
    public void setFunctions(SubProgramDeclarationsNode functions) {
        this.functions = functions;
    }

    /**
     * Gets the CompoundStatementNode
     * @return CompoundStatementNode
     */
    public CompoundStatementNode getMain() {
        return main;
    }

    /**
     * Sets the CompoundStatementNode
     * @param main CompoundStatementNode
     */
    public void setMain(CompoundStatementNode main) {
        this.main = main;
    }

    /**
     * Gets the program's name
     * @return String, name
     */
    public String getName() {return name;}

    /**
     * Sets the list of VariableNodes
     * @param list ArrayList<VariableNode>
     */
    public void setArguments(ArrayList<VariableNode> list) {
        this.list = list;
    }

    /**
     * Generates the Tree to the console for Program
     * @param level int
     * @return String
     */


    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Program: " + name + "\n";
        answer += variables.indentedToString( level + 1);
        answer += functions.indentedToString( level + 1);
        answer += main.indentedToString( level + 1);
        return answer;
    }
}
