package AST;
import AST.Visitor.Visitor;
import lab7.*;
public class BinaryOPNode extends AbstractNode {
    public AbstractNode number1;
    public AbstractNode number2;
    String operator;

    public BinaryOPNode (AbstractNode number1, AbstractNode number2, String operator) {
        this.number1 = number1;
        this.number2 = number2;
        this.operator = operator;


        node.put("left", number1.node);
        node.put("right", number2.node);
        node.put("type", "BinaryExpression");
        node.put("operator", operator);

    }
    public String getName() {return number1 + " " + operator + " " + number2;}
    public void accept(Visitor v) {
        v.visit(this);
    }
}