package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONArray;

public class ForLoopNode extends AbstractNode {

    public AbstractNode initCase;
    public AbstractNode testCase;
    public AbstractNode continueCase;
    public AbstractNode body;

    public JSONArray bodyList = new JSONArray();

    public ForLoopNode(AbstractNode initCase, AbstractNode testCase, AbstractNode continueCase, AbstractNode body) {
        this.initCase = initCase;
        this.testCase = testCase;
        this.continueCase = continueCase;
        this.body = body;
        node.put("type", "ForLoop");
        node.put("test",testCase.node);
        node.put("init", initCase.node);
        node.put("continue",continueCase.node);
        node.put("body", bodyList);
        addBodyToList (body.getFirst());
    }

    public void addBodyToList (AbstractNode n) {
        bodyList.add(n.node);
        if(n.getSib() != null) addBodyToList(n.getSib());
    }
    @Override
    public void accept(Visitor analyzer) {
        analyzer.visitForLoopNode(this);
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        analyzer.visitForLoopNode(this,parent);
    }
}
