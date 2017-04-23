package analysis;

import java.util.ArrayList;

import scanner.TokenType;
import syntaxtree.ArrayNode;
import syntaxtree.AssignmentStatementNode;
import syntaxtree.CompoundStatementNode;
import syntaxtree.ExpressionNode;
import syntaxtree.IfStatementNode;
import syntaxtree.OperationNode;
import syntaxtree.ProcedureStatementNode;
import syntaxtree.ProgramNode;
import syntaxtree.StatementNode;
import syntaxtree.SubProgramDeclarationsNode;
import syntaxtree.SubProgramNode;
import syntaxtree.UnaryOpNode;
import syntaxtree.ValueNode;
import syntaxtree.VariableNode;
import syntaxtree.WhileStatementNode;

/**
 * Semantic Analyzer of the Expression Compiler.
 * This class can perform the code folding analysis on an expression tree.
 * @author Don Vo
 */
public class SemanticAnalyzer {

	/** Root node of the tree to analyze. */
	private ProgramNode root;

	/**
	 * Creates a SemanticAnalyzer with the given expression.
	 * @param tree An expression tree to analyze.
	 */
	public SemanticAnalyzer(ProgramNode tree) {this.root = tree;}

	/**
	 * Performs code folding on the expression tree.
	 * For demonstration purposes, we only fold addition nodes.
	 * @return The root node of the mutated tree.
	 */
	public ProgramNode codeFolding() {
		SubProgramDeclarationsNode foldedSNode = new SubProgramDeclarationsNode();
		for(SubProgramNode subNode:root.getFunctions().getProcs()){
			foldedSNode.addSubProgramDeclaration(foldSubProgram(subNode));
		}
		CompoundStatementNode foldedCNode = new CompoundStatementNode();
		for(StatementNode subNode:root.getMain().getStatements()){
			foldedCNode.addStatement(foldState(subNode));
		}
		root.setFunctions(foldedSNode);
		root.setMain(foldedCNode);
		return root;
	}

	/**
	 * Folds code for the given node.
	 * We only fold if both children are value nodes, and the node itself
	 * is a PLUS, MINUS, ASTERISK, or FORWARD_SLASH node.
	 * @param node The node to check for possible efficiency improvements.
	 * @return The folded node or the original node if nothing 
	 */
	public ExpressionNode foldOpNode( OperationNode node) {
		if(node.getLeft() instanceof OperationNode) {
			node.setLeft( foldOpNode( (OperationNode)node.getLeft()));
		}
		if(node.getRight() instanceof OperationNode) {
			node.setRight( foldOpNode( (OperationNode)node.getRight()));
		}
		if(node.getLeft() instanceof UnaryOpNode){
			//TODO: Fold UnaryOpNode
		}
		if(node.getRight() instanceof UnaryOpNode){
			//TODO: Fold UnaryOpNode
		}
		if( node.getLeft() instanceof ValueNode &&
				node.getRight() instanceof ValueNode) {
			if(node.getOperation() == TokenType.PLUS){
				int leftValue = Integer.parseInt(((ValueNode)node.getLeft()).getAttribute());
				int rightValue = Integer.parseInt(((ValueNode)node.getRight()).getAttribute());
				ValueNode vn = new ValueNode( "" + (leftValue + rightValue));
				return vn;
			}
			else if(node.getOperation() == TokenType.MINUS){
				int leftValue = Integer.parseInt(((ValueNode)node.getLeft()).getAttribute());
				int rightValue = Integer.parseInt(((ValueNode)node.getRight()).getAttribute());
				ValueNode vn = new ValueNode( "" + (leftValue - rightValue));
				return vn;
			}
			else if(node.getOperation() == TokenType.ASTERISK){
				int leftValue = Integer.parseInt(((ValueNode)node.getLeft()).getAttribute());
				int rightValue = Integer.parseInt(((ValueNode)node.getRight()).getAttribute());
				ValueNode vn = new ValueNode( "" + (leftValue * rightValue));
				return vn;
			}
			else{
				int leftValue = Integer.parseInt(((ValueNode)node.getLeft()).getAttribute());
				int rightValue = Integer.parseInt(((ValueNode)node.getRight()).getAttribute());
				ValueNode vn = new ValueNode( "" + (leftValue / rightValue));
				return vn;
			}
		}
		else {
			return node;
		}
	}
	/**
	 * Folds code for the given node.
	 * Folds the same as ProgramNode
	 * @param node The node to check for possible efficiency improvements.
	 * @return The folded node or the original node if nothing 
	 */
	public SubProgramNode foldSubProgram( SubProgramNode node) {
		SubProgramDeclarationsNode foldedSNode = new SubProgramDeclarationsNode();
		for(SubProgramNode subNode : node.getFunctions().getProcs()){
			foldedSNode.addSubProgramDeclaration(foldSubProgram(subNode));
		}
		CompoundStatementNode foldedCNode = new CompoundStatementNode();
		for(StatementNode subNode : node.getMain().getStatements()){
			foldedCNode.addStatement(foldState(subNode));
		}

		node.setFunctions(foldedSNode);
		node.setMain(foldedCNode);
		return node;
	}
	/**
	 * Folds code for the given node.
	 * Folds all expressions within all statement nodes.
	 * This includes AssignmentStatementNode, CompoundStatementNode, IfStatementNode
	 * ProcedureStatementNode, and WhileStatementNode.
	 * @param node, The node to check for possible efficiency improvements.
	 * @return The folded node or the original node if nothing 
	 */
	public StatementNode foldState( StatementNode node) {
		String classType = node.getClass().getSimpleName();
		if(classType.equals("AssignmentStatementNode")){
			((AssignmentStatementNode)node).setExpression(checkExpress(((AssignmentStatementNode)node).getExpression()));
			((AssignmentStatementNode)node).setLvalue(foldVars(((AssignmentStatementNode)node).getLvalue()));
			return node;
		}
		else if(classType.equals("CompoundStatementNode")){
			for(StatementNode sNode:((CompoundStatementNode)node).getStatements()){
				foldState(sNode);
			}
			return node;
		}
		else if(classType.equals("IfStatementNode")){
			((IfStatementNode)node).setTest(checkExpress(((IfStatementNode)node).getTest()));
			foldState(((IfStatementNode)node).getThenStatement());
			foldState(((IfStatementNode)node).getElseStatement());
			return node;
		}
		else if(classType.equals("ProcedureStatementNode")){
			ArrayList<ExpressionNode> newList = new ArrayList<ExpressionNode>();
			for(ExpressionNode eNode:((ProcedureStatementNode)node).getList()){
				newList.add(checkExpress(eNode));
			}
			((ProcedureStatementNode)node).setList(newList);
			return node;
		}
		else if(classType.equals("WhileStatementNode")){
			((WhileStatementNode)node).setTest(checkExpress(((WhileStatementNode)node).getTest()));
			foldState(((WhileStatementNode)node).getDoStatement());
			return node;
		}
		return node;
	}
	/**
	 * Checks to see if the ExpressionNode passed in is an OperationNode
	 * or an UnaryOpNode. If either, fold the passed in node.
	 * If neither, returns the ExpressionNode passed in.
	 * @param node, The node that might be reduced
	 * @return node, The folded or original node.
	 */
	public ExpressionNode checkExpress(ExpressionNode node){
		if(node instanceof OperationNode){
			return foldOpNode((OperationNode)node);
		}
		if(node instanceof UnaryOpNode){
			//TODO: Fold UnaryOpNodes
		}
		return node;
	}
	/**
	 * Checks if the VariableNode passed in is an ArrayNode.
	 * If so, fold the array. Otherwise return the VariableNode.
	 * @param node, The node that might be reduced
	 * @return node,  The folded or original node.
	 */
	public VariableNode foldVars(VariableNode node){
		if(node.getClass().getSimpleName().equals("ArrayNode")){
			((ArrayNode)node).setNode(checkExpress(((ArrayNode)node).getNode()));
			return node;
		}
		return node;
	}
}
