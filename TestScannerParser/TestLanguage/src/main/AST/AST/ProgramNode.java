package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class ProgramNode extends AbstractNode {
    public VariableDeclarationNode decls;

    public ProgramNode (VariableDeclarationNode decls) {
        this.decls = decls;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

}
