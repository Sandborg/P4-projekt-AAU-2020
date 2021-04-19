package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class ProgramNode extends AbstractNode {
    public AbstractNode decls;

    public ProgramNode (AbstractNode n) {
        this.decls = n;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

}
