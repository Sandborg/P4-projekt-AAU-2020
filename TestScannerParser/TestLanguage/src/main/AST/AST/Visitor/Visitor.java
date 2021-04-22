package AST.Visitor;

import AST.*;
import lab7.AbstractNode;

public interface Visitor {
    public void visit(VariableDeclarationNode n);
    public void visit(VariableDeclarationNode n, AbstractNode parent);
    public void visit(IdentifierNode n);
    public void visit(IdentifierNode n, AbstractNode parent);
    public void visit(ProgramNode n);
    public void visit(ProgramNode n, AbstractNode parent);
    public void visit(BinaryOPNode n);
    public void visit(BinaryOPNode n, AbstractNode parent);
    public void visit(TypeNode n);
    public void visit(TypeNode n, AbstractNode parent);
    public void visit(SingleNode n);
    public void visit(SingleNode n, AbstractNode parent);
    public void visit(IntegerNode n);
    public void visit(IntegerNode n, AbstractNode parent);
    public void visit(FloatNode n);
    public void visit(FloatNode n, AbstractNode parent);
    public void visit(AssignmentNode n);
    public void visit(AssignmentNode n, AbstractNode parent);
    public void visit(FunctionDecNode n);
    public void visit(FunctionDecNode n, AbstractNode parent);

}
