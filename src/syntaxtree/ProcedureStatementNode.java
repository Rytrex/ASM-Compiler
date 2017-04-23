package syntaxtree;

import java.util.ArrayList;

/**
 * ProcedureStatementNode holds a variable and a list of expressions
 * @author Don Vo
 */
public class ProcedureStatementNode extends StatementNode{

	//Declaring variables
	private VariableNode name;
	private ArrayList<ExpressionNode> list = new ArrayList<ExpressionNode>();

	/**
	 * Takes in a VariableNode and initializes the ProcedureStatement
	 * @param name
	 */
	public ProcedureStatementNode(VariableNode name){
		this.name = name;
	}

	/**
	 * Returns the VariableNode name
	 * @return VariableNode
	 */
	public VariableNode getName(){
		return name;
	}
	/**
	 * Returns the list of ExpressionNodes
	 * @return ArrayList<ExpressionNode>
	 */
	public ArrayList<ExpressionNode> getList(){
		return list;
	}

	/**
	 * Takes in a list of ExpressionNodes. If any nodes are null
	 * do not add them to the list.
	 * @param list
	 */
	public void setList(ArrayList<ExpressionNode> list){
		for(ExpressionNode express : list){
			if(express != null){
				this.list.add(express);
			}
		}
	}
	/**
	 * Generates the Tree to the console for ProcedureStatements
	 * @param level int
	 * @return String
	 */
	@Override
	public String indentedToString(int level) {
		String answer = this.indentation( level);
		answer += "Procedure Statement\n";
		answer += this.name.indentedToString( level + 1);
		return answer;
	}

}
