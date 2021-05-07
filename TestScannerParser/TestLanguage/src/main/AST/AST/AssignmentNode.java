package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONObject;

public class AssignmentNode extends AbstractNode {
    public AbstractNode set;
    public AbstractNode to;
    public String varType;
    public AssignmentNode(AbstractNode set, AbstractNode to) {
        this.set = set;
        this.to = to;
        node.put("type", "AssignmentExpression");
        node.put("left", set.node);
        node.put("right", to.node);
    }

    public void UpdateRightNode (String s) {
        JSONObject right = new JSONObject();
        varType = s;
        right.put("value", "\"" + s + "\"");
        right.put("type","string");
        node.put("right", right);
    }

    public String getType() {return set.getType();}
    public void accept(Visitor v) {
        v.visitAssign(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitAssign(this, parent);
    }

}
