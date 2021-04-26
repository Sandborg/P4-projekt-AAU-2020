package AST;

import AST.Visitor.Visitor;
import lab7.*;
public class IntegerNode extends AbstractNode {
    int value;
    public IntegerNode(int value) {
        this.value = value;
        node.put("type", "int");
        node.put("value", value);

    }
    public String getName() {return "int";}
    public int getValue() {return value;}
    public void accept(Visitor v) {
        v.visitInt(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitInt(this, parent);
    }

}