package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class ConditionNode extends AbstractNode {
    public  AbstractNode left;
    public AbstractNode right;
    public String operator;
    public  ConditionNode (String operator, AbstractNode left, AbstractNode right) {
        this.left = left;
        this.right = right;
        this.operator = operator;

        node.put("operator", operator);
        node.put("left", left.node);
        node.put("right", right.node);
        node.put("type", "LogicalExpression");
    }

    @Override
    public void accept(Visitor analyzer) {
        analyzer.visitConditionNode(this);
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        analyzer.visitConditionNode(this,parent);
    }
}
