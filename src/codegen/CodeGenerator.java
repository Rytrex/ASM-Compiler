package codegen;

import parser.SymbolTable;
import scanner.TokenType;
import syntaxtree.*;

/**
 * CodeGenerator creates MIPS Assembly code based on a Syntax Tree and Symbol Table
 * @author Don Vo
 */
public class CodeGenerator {

    private ProgramNode root;
    private SymbolTable symTables;
    private String fileString = "";

    /**
     * Sets up the CodeGenerator with the generated ProgramNode tree and SymbolTable table.
     * @param node ProgramNode
     * @param table SymbolTable
     */
    public CodeGenerator(ProgramNode node, SymbolTable table){
        this.root = node;
        this.symTables = table;
    }

    /**
     * Used to generate the MIPS Assembly code for (some of) the tree's nodes
     * @return String, Text of the MIPS Assembly file
     */
    public String generateCode(){
        //Generate global variables declared in the Pascal code and a newLine character
        fileString += ".data\n" +
                "__newLine__: .asciiz \"\\n\"\n";
        for(VariableNode node:root.getVariables().getVars()){
            fileString += node.getName() + ": .word 0\n";
        }

        //Generates write and main functions
        fileString += "\n.text\n" +
                "write:\n" +
                "addi $sp, $sp, -4\n" +
                "sw $ra, 0($sp)\n" +
                "li $v0, 1\n" +
                "syscall\n" +
                "li $v0, 4\n" +
                "la $a0, __newLine__\n" +
                "syscall\n" +
                "lw $ra, 0($sp)\n" +
                "addi $sp, $sp, 4\n" +
                "jr $ra\n\n" +
                "main:\n" +
                "addi $sp, $sp, -4\n" +
                "sw $ra, 0($sp)\n";
        //Handles all statements in the program
        for(StatementNode statement:root.getMain().getStatements()){
            statementGenerator(statement);
        }
        //Cleaning up main function
        fileString += "lw $ra, 0($sp)\n" +
                "addi $sp, $sp, 4\n" +
                "jr $ra\n";
        return fileString;
    }
    private void statementGenerator(StatementNode node){
        if(node instanceof AssignmentStatementNode){
            generateAssignment((AssignmentStatementNode)node);
        }
        else if(node instanceof CompoundStatementNode){
            for(StatementNode currentStatement:((CompoundStatementNode)node).getStatements()){
                statementGenerator(currentStatement);
            }
        }
        else if(node instanceof WriteNode){
            generateWrite((WriteNode)node);
        }
    }
    private void generateAssignment(AssignmentStatementNode node){
        //tValue holds the current $t register position
        int tValue = 0;
        //Load immediate and store it if ValueNode
        if(node.getExpression() instanceof ValueNode){
            fileString += "li $t0, " + ((ValueNode)node.getExpression()).getAttribute() + "\n" +
                    "sw $t0, " + node.getLvalue().getName() + "\n";
        }
        //Store value into variable
        else{
            generateExpression(node.getExpression(), tValue);
            fileString += "sw $t" + tValue + ", " + node.getLvalue().getName() + "\n";
        }
    }
    private int generateExpression(ExpressionNode node, int tValue){
        //Generate OperationNode code
        if(node instanceof OperationNode){
            tValue = generateOperation((OperationNode)node, tValue);
        }
        //Generate VariableNode code
        else if(node instanceof VariableNode){
            //Loads saved value into $t register
            fileString += "lw $t" + tValue + ", " + ((VariableNode)node).getName()+ "\n";
        }
        //Generate ValueNode code
        else if(node instanceof ValueNode){
            //Loads new number into $t register
            fileString += "li $t" + tValue + ", " + ((ValueNode)node).getAttribute() + "\n";
        }
        //Returns the value of the register the result is stored in
        return tValue;
    }

    private int generateOperation(OperationNode node, int tValue){
        //tLeftValue stores the $t register the left value is stored in
        int tLeftValue = 0;
        //tRightValue stores the $t register the right value is stored in
        int tRightValue = 0;

        //Generates OperationNode code if the left side is an operation
        if(node.getLeft() instanceof OperationNode){
            tLeftValue = generateOperation((OperationNode)node.getLeft(), tValue);
        }
        //Otherwise generate ExpressionNode code
        else{
            tLeftValue = generateExpression(node.getLeft(), tValue);
        }

        //Generates OperationNode code if the right side is an operation
        if(node.getRight() instanceof OperationNode){
            tRightValue = generateOperation((OperationNode)node.getRight(), (tValue + 1));
        }
        //Otherwise generate ExpressionNode code
        else{
            tRightValue = generateExpression(node.getRight(), (tValue + 1));
        }

        //Computes operation depending on it's TokenType and stores it into tValue
        TokenType type = node.getOperation();
        if(type == TokenType.FORWARD_SLASH || type == TokenType.DIV){
            fileString += "div $t" + tLeftValue + ", $t" + tRightValue + "\n" +
                    "mflo $t" + tValue + "\n";
        }
        else if(type == TokenType.ASTERISK){
            fileString += "mult $t" + tLeftValue + ", $t" + tRightValue + "\n" +
                    "mflo $t" + tValue + "\n";
        }
        else if( type == TokenType.PLUS){
            fileString += "add $t" + tValue + ", $t" + tLeftValue + ", $t" + tRightValue + "\n";
        }
        else if( type == TokenType.MINUS){
            fileString += "sub $t" + tValue + ", $t" + tLeftValue + ", $t" + tRightValue + "\n";
            //case modulus, the result is stored in the high register after division.
        }
        else if( type == TokenType.MOD){
            fileString += "div $t" + tLeftValue + ", $t" + tRightValue + "\n" +
                    "mfhi $t" + (tRightValue + 1) + "\n";
        }
        return tValue;
    }
    private void generateWrite(WriteNode node){
        //Generates ExpressionNode code then jump and link to write function
        int tValue = generateExpression(node.getExpression(), 0);
        fileString += "add $a0, $zero, $t" + tValue +
                "\njal write\n";
    }
}