package AST;

import AST.Visitor.Visitor;
import lab7.*;

public class IdentifierNode extends AbstractNode {
    public String id;

    public IdentifierNode (String id) {
        this.id = id;
        node.put("type", "Identifier");
        node.put("id", id);
    }
    public String getName() {return id;}

    public void accept(Visitor v) {
        v.visitId(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitId(this, parent);
    }

}