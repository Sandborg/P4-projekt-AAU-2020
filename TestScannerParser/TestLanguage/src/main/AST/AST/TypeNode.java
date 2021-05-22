package AST;

import AST.Visitor.Visitor;

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
        v.visitType(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitType(this, parent);
    }

}