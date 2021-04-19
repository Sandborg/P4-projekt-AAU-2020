package AST;

import AST.Visitor.Visitor;
import lab7.*;

public class FloatNode extends AbstractNode {
    float value;
    public FloatNode(float value) {
        this.value = value;
        node.put("type", "Decimal");
        node.put("value", value);

    }

    public float getValue() {return value;}
    public void accept(Visitor v) {
        v.visit(this);
    }
}