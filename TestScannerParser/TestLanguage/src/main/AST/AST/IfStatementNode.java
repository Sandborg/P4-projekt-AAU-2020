package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONObject;

public class IfStatementNode extends AbstractNode {

    public  AbstractNode ifBody;
    public AbstractNode elseBody;
    public AbstractNode test;
    public IfStatementNode (AbstractNode test, AbstractNode ifBody) {
        this.test = test;
        this.elseBody = elseBody;
        this.ifBody = ifBody;
        node.put("type", "IfStatement");
        node.put("ifBody", ifBody.node);
        node.put("test", test.node);
    }

    public IfStatementNode (AbstractNode test, AbstractNode ifBody, AbstractNode elseBody) {
            this.elseBody = elseBody;
            this.ifBody = ifBody;
            this.test = test;
            node.put("type", "IfStatement");
            node.put("test", test.node);
            node.put("ifBody", ifBody.node);
            node.put("elseBody", elseBody.node);
    }

    public void accept(Visitor v) {
        v.visitIfStatement(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitIfStatement(this, parent);
    }
}
