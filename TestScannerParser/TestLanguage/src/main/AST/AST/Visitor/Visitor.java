package AST.Visitor;

import AST.*;
import lab7.AbstractNode;

public interface Visitor {
    public void visitVarDec(VariableDeclarationNode n);
    public void visitVarDec(VariableDeclarationNode n, AbstractNode parent);
    public void visitId(IdentifierNode n);
    public void visitId(IdentifierNode n, AbstractNode parent);
    public void visitProg(ProgramNode n);
    public void visitProg(ProgramNode n, AbstractNode parent);
    public void visitBinaryOP(BinaryOPNode n);
    public void visitBinaryOP(BinaryOPNode n, AbstractNode parent);
    public void visitType(TypeNode n);
    public void visitType(TypeNode n, AbstractNode parent);
    public void visitInt(IntegerNode n);
    public void visitInt(IntegerNode n, AbstractNode parent);
    public void visitFloat(FloatNode n);
    public void visitFloat(FloatNode n, AbstractNode parent);
    public void visitAssign(AssignmentNode n);
    public void visitAssign(AssignmentNode n, AbstractNode parent);
    public void visitFuncDec(FunctionDecNode n);
    public void visitFuncDec(FunctionDecNode n, AbstractNode parent);
    public void visitFuncDef(FunctionDefNode n);
    public void visitFuncDef(FunctionDefNode n, AbstractNode parent);
    public void visitReturnStatement(ReturnStatementNode n);
    public void visitReturnStatement(ReturnStatementNode n, AbstractNode parent);
    public void visitFunctionCall(FunctionCallNode n);
    public void visitFunctionCall(FunctionCallNode n, AbstractNode parent);

}
