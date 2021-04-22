package AST;

import AST.Visitor.Visitor;
import lab7.*;

public class SingleNode extends AbstractNode {
    String name;


    public SingleNode(String name) {
        this.name = name;
    }
    public String getName() {return name;}
    public void accept(Visitor v) {
        v.visit(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visit(this, parent);
    }

}