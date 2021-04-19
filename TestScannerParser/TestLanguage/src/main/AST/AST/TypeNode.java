package AST;

import AST.Visitor.Visitor;
import lab7.*;

public class TypeNode extends AbstractNode {
    String type;

    public TypeNode (String type) {
        this.type = type;
        node.put("type", "Type");
        node.put("dataType", type);
    }

    public String getValue() {return type;}

    public void accept(Visitor v) {
        v.visit(this);
    }
}