package AST;

import AST.Visitor.Visitor;
import lab7.*;

public class TypeNode extends AbstractNode {
    public String type;

    public TypeNode (String type) {
        this.type = type;
        node.put("type", "Type");
        node.put("dataType", type);
    }

    public String getValue() {return type;}
    public String getName() {return type;}
    public void accept(Visitor v) {
        v.visit(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visit(this, parent);
    }

}