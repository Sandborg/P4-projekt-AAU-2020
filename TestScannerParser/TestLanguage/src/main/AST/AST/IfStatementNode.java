package AST;

import AST.Visitor.Visitor;
import org.json.simple.JSONArray;

public class IfStatementNode extends AbstractNode {

    public  AbstractNode ifBody;
    public AbstractNode elseBody;
    public AbstractNode test;

    public JSONArray ifBodyList = new JSONArray();
    public JSONArray elseBodyList = new JSONArray();
    public IfStatementNode (AbstractNode test, AbstractNode ifBody) {
        this.test = test;
        this.ifBody = ifBody;

        node.put("type", "IfStatement");
        node.put("ifBody", ifBodyList);
        node.put("test", test.node);

        addBodyToList (ifBody.getFirst(),ifBodyList);
    }

    public IfStatementNode (AbstractNode test, AbstractNode ifBody, AbstractNode elseBody) {
        this.elseBody = elseBody;
        this.ifBody = ifBody;
        this.test = test;
        node.put("type", "IfStatement");
        node.put("test", test.node);
        node.put("ifBody", ifBodyList);
        node.put("elseBody", elseBodyList);

        addBodyToList (ifBody.getFirst(),ifBodyList);
        addBodyToList (elseBody.getFirst(),elseBodyList);
    }

    public void addBodyToList (AbstractNode n, JSONArray list) {
        list.add(n.node);
        if(n.getSib() != null) addBodyToList(n.getSib(), list);
    }

    public void accept(Visitor v) {
        v.visitIfStatement(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitIfStatement(this, parent);
    }
}
