package AST;

import AST.Visitor.Visitor;

public class IntegerNode extends AbstractNode {
    int value;
    public IntegerNode(int value) {
        this.value = value;
        node.put("type", "int");
        node.put("value", value);

    }
    public String getName() {return "int";}
    public int getValue() {return value;}
    public String getValueString() {
        return String.valueOf(value);
    }

    public void accept(Visitor v) {
        v.visitInt(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        try {
            v.visitInt(this, parent);
        }catch(Exception e) {
            System.out.print(e + "\n");
            System.exit(0);

        }
    }

}