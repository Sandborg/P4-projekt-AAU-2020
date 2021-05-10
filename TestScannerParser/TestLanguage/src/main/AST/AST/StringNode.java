package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class StringNode  extends AbstractNode {
    String value;

    public StringNode (String value) {
        this.value = value;
        node.put("type", "string");
        node.put("value", "\"" + value + "\"");
    }

    public String getName() {return "string";}
    public String getValue() {return value;}
    public String getValueString() {return value;}

    public void accept(Visitor v) {
        v.visitStringNode(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitStringNode(this, parent);
    }
}


