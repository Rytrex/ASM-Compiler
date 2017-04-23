package compilermain;

import analysis.SemanticAnalyzer;
import codegen.CodeGenerator;
import parser.*;
import syntaxtree.ProgramNode;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
	/**
	 * The main method that runs and controls the compiler
	 * @param args, CLI inputs
	 */
	public static void main(String[] args){
		//Setting up parser
		Parser p = new Parser(args[0], true);
		//Building tree
		ProgramNode node = p.program();
		//Print out tree
		System.out.println("After tree built \n" + node.indentedToString(0));
		//Setting up code folding
		SemanticAnalyzer sa = new SemanticAnalyzer(node);
		//Fold tree
		sa.codeFolding();
		//Print folded tree
		System.out.println("After code folding \n" + node.indentedToString(0));
		CodeGenerator gen = new CodeGenerator(node, p.getSymTable());
		try {
			PrintWriter out = new PrintWriter(new FileWriter(node.getName() + ".asm"));
			out.printf(gen.generateCode());
			out.close();
		}
		catch(Exception e){
			System.out.println("Invalid file location");
		}
	}
}
