package AST.Visitor;

import AST.*;
import Exceptions.*;
import AST.AbstractNode;

public interface Visitor {
    public void visitVarDec(VariableDeclarationNode n) throws VarAlreadyDeclaredException;
    public void visitVarDec(VariableDeclarationNode n, AbstractNode parent) throws VarAlreadyDeclaredException;
    public void visitId(IdentifierNode n) throws VarNotFoundException;
    public void visitId(IdentifierNode n, AbstractNode parent) throws VarNotFoundException, WrongTypeException;
    public void visitProg(ProgramNode n);
    public void visitProg(ProgramNode n, AbstractNode parent);
    public void visitBinaryOP(BinaryOPNode n);
    public void visitBinaryOP(BinaryOPNode n, AbstractNode parent);
    public void visitType(TypeNode n);
    public void visitType(TypeNode n, AbstractNode parent);
    public void visitInt(IntegerNode n);
    public void visitInt(IntegerNode n, AbstractNode parent) throws WrongTypeException;
    public void visitFloat(FloatNode n);
    public void visitFloat(FloatNode n, AbstractNode parent) throws WrongTypeException;
    public void visitAssign(AssignmentNode n) throws PointerException;
    public void visitAssign(AssignmentNode n, AbstractNode parent) throws PointerException;
    public void visitFuncDec(FunctionDecNode n);
    public void visitFuncDec(FunctionDecNode n, AbstractNode parent);
    public void visitFuncDef(FunctionDefNode n) throws MissingReturnException, WrongTypeException, WrongParamsException;
    public void visitFuncDef(FunctionDefNode n, AbstractNode parent);
    public void visitReturnStatement(ReturnStatementNode n);
    public void visitReturnStatement(ReturnStatementNode n, AbstractNode parent) throws WrongTypeException;
    public void visitFunctionCall(FunctionCallNode n) throws WrongParamsException;
    public void visitFunctionCall(FunctionCallNode n, AbstractNode parent) throws WrongParamsException;
    public void visitIfStatement(IfStatementNode n);
    public void visitIfStatement(IfStatementNode n, AbstractNode parent);
    public void visitConditionNode(ConditionNode n);
    public void visitConditionNode(ConditionNode n, AbstractNode parent);
    public void visitStringNode(StringNode n);
    public void visitStringNode(StringNode n, AbstractNode parent) throws WrongTypeException;
    public void visitImportNode(ImportNode n);
    public void visitImportNode(ImportNode n, AbstractNode parent);
    public void visitForLoopNode(ForLoopNode n);
    public void visitForLoopNode(ForLoopNode n, AbstractNode parent);
}
