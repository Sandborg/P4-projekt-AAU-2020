package AST;

import AST.Visitor.Visitor;
import lab7.*;

public class FloatNode extends AbstractNode {
    float value;
    public FloatNode(float value) {
        this.value = value;
        node.put("type", "decimal");
        node.put("value", value);

    }
    public String getName() {return "decimal";}
    public float getValue() {return value;}
    public String getValueString() {return String.valueOf(value);}

    public void accept(Visitor v) {
        v.visitFloat(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitFloat(this, parent);
    }

}