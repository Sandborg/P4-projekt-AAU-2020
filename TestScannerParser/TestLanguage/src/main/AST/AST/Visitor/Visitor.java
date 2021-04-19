package AST.Visitor;

import AST.*;

public interface Visitor {
    public void visit(VariableDeclarationNode n);
    public void visit(IdentifierNode n);
    public void visit(ProgramNode n);
    public void visit(BinaryOPNode n);
    public void visit(TypeNode n);
    public void visit(SingleNode n);
    public void visit(IntegerNode n);
    public void visit(FloatNode n);
    public void visit(AssignmentNode n);
}
