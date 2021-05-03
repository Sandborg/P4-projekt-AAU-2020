package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONObject;

public class IfStatementNode extends AbstractNode {

    public  AbstractNode body;

    public IfStatementNode (AbstractNode test) {
        node.put("type", "IfStatement");
        node.put("test", test.node);
    }

    public void accept(Visitor v) {
        v.visitIfStatement(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitIfStatement(this, parent);
    }
}
