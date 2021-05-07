package AST;
import AST.Visitor.Visitor;
import lab7.*;
public class BinaryOPNode extends AbstractNode {
    public AbstractNode number1;
    public AbstractNode number2;
    public String operator;
    public float result;
    public BinaryOPNode (AbstractNode number1, AbstractNode number2, String operator) {
        this.number1 = number1;
        this.number2 = number2;
        this.operator = operator;


        node.put("left", number1.node);
        node.put("right", number2.node);
        node.put("type", "BinaryExpression");
        node.put("operator", operator);

        if(!(number1 instanceof StringNode) && !(number2 instanceof StringNode)) {
            float n1 = 0;
            float n2 = 0;
            if(number1 instanceof IntegerNode ||number1 instanceof FloatNode) {
                n1 = Float.parseFloat(number1.getValueString());
            }else if(number1 instanceof BinaryOPNode) {
                BinaryOPNode bn = (BinaryOPNode) number1;
                n1 = bn.result;
            }
            if(number2 instanceof IntegerNode ||number2 instanceof FloatNode) {
                n2 = Float.parseFloat(number2.getValueString());
            }else if(number2 instanceof BinaryOPNode) {
                BinaryOPNode bn = (BinaryOPNode) number2;
                n2 = bn.result;
            }
            if(operator == "+") {
                result = n1 + n2;
            }else if(operator == "-") {
                result = n1 - n2;
            }else if(operator == "%") {
                result = n1 % n2;
            }else if(operator == "*") {
                result = n1 * n2;
            }else if(operator == "/") {
                result = n1 / n2;
            }
        }
        node.put("result",result);
    }

    public String getNumber1Type() {return number1.getName();}
    public String getNumber2Type() {return number2.getName();}
    public String getName() {return number1 + " esbjerg " + operator + " " + number2;}
    public void accept(Visitor v) {
        v.visitBinaryOP(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitBinaryOP(this, parent);
    }

}