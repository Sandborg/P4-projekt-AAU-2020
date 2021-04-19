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
    public String getName() {return "Decimal";}
    public float getValue() {return value;}
    public void accept(Visitor v) {
        v.visit(this);
    }
}