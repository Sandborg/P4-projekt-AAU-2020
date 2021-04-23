package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONArray;

public class FunctionCallNode extends AbstractNode {
    public AbstractNode id;
    public AbstractNode params;

    public JSONArray paramList = new JSONArray();

    public  FunctionCallNode(AbstractNode id, AbstractNode params) {
        this.id = id;
        this.params = params;

        node.put("Type", "FunctionCall");
        node.put("id", id.node);
        node.put("params", paramList);
        addParamsToList(params.getFirst());
    }

    public  FunctionCallNode(AbstractNode id) {
        this.id = id;

        node.put("Type", "FunctionCall");
        node.put("id", id.node);
    }

    public  void addParamsToList (AbstractNode n) {
        paramList.add(n.node);
        if(n.getSib() != null) addParamsToList(n.getSib());
    }

    @Override
    public void accept(Visitor analyzer) {
        analyzer.visitFunctionCall(this);
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        analyzer.visitFunctionCall(this,parent);
    }
}
