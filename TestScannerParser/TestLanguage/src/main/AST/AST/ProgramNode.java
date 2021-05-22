package AST;

import AST.Visitor.Visitor;

public class ProgramNode extends AbstractNode {
    public AbstractNode decls;

    public ProgramNode (AbstractNode n) {
        this.decls = n;
    }

    public void accept(Visitor v) {
        v.visitProg(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitProg(this, parent);
    }

}
