
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a collection of subprogram declarations
 * @author Don Vo based off of Erik Steinmetz
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode {
    
	/**Holds all subprograms*/
    private ArrayList<SubProgramNode> procs = new ArrayList<SubProgramNode>();
    
    /**
     * Adds a subprogram to the list
     * @param aSubProgram, new subprogram to be added
     */
    public void addSubProgramDeclaration( SubProgramNode aSubProgram) {
        procs.add( aSubProgram);
    }
    
    /**
     * Adds a list of subprograms to the current list
     * @param aSubProgram, list of SubProgramNodes
     */
    public void addSubProgramDeclaration( SubProgramDeclarationsNode aSubProgram) {
        procs.addAll( aSubProgram.procs);
    }

    /**
     * Gets the list of SubProgramNodes
     * @return ArrayList<SubProgramNode>
     */
    public ArrayList<SubProgramNode> getProcs(){
    	return procs;
    }

    /**
     * Generates the Tree to the console for SubProgramDeclarations
     * @param level int
     * @return String
     */
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "SubProgramDeclarations\n";
        for( SubProgramNode subProg : procs) {
            answer += subProg.indentedToString( level + 1);
        }
        return answer;
    }
    
}
