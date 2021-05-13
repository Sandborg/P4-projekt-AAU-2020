package AST;

import AST.Visitor.Visitor;
import Exceptions.WrongTypeException;
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
        try {
            v.visitStringNode(this, parent);
        } catch (WrongTypeException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}


