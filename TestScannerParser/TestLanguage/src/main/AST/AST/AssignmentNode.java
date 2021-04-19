package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class AssignmentNode extends AbstractNode {
    public AbstractNode set;
    public AbstractNode to;

    public AssignmentNode(AbstractNode set, AbstractNode to) {
        this.set = set;
        this.to = to;
        node.put("type", "AssignmentExpression");
        node.put("left", set.node);
        node.put("right", to.node);
    }
    public void accept(Visitor v) {
        v.visit(this);
    }
}
