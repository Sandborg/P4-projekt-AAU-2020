package AST;

import AST.Visitor.Visitor;
import lab7.*;
public class IntegerNode extends AbstractNode {
    int value;
    public IntegerNode(int value) {
        this.value = value;
        node.put("type", "Literal");
        node.put("value", value);

    }

    public int getValue() {return value;}
    public void accept(Visitor v) {
        v.visit(this);
    }
}