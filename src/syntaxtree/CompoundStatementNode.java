
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a compound statement in Pascal.
 * A compound statement is a block of zero or more
 * statements to be run sequentially.
 * @author Don Vo based off of Erik Steinmetz
 */
public class CompoundStatementNode extends StatementNode {

	/**Holds the list of StatementNodes*/
	private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();

	/**
	 * Adds new statements from an ArrayList that are not NULL
	 * @param state, a list of StatementNodes
	 */
	public void addStatement( ArrayList<StatementNode> state) {
		if(state != null){
			for(StatementNode in: state){
				if(in != null){
					this.statements.add(in);
				}
			}
		}
	}
	/**
	 * Adds a new statement to the list of statements
	 * @param state, StatementNode
	 */
	public void addStatement( StatementNode state) {
		this.statements.add(state);
	}

	/**
	 * Gets the ArrayList of Statements
	 * @return ArrayList<Statements>
	 */
	public ArrayList<StatementNode> getStatements() {
		return statements;
	}

	/**
	 * Generates the Tree to the console for CompoundStatement
	 * @param level int
	 * @return String
	 */
	public String indentedToString( int level) {
		String answer = this.indentation( level);
		answer += "Compound Statement\n";
		for( StatementNode state : statements) {
			answer += state.indentedToString( level + 1);
		}
		return answer;
	}
}
