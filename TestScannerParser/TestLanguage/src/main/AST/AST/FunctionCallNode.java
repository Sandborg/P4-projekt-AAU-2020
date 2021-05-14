package AST;

import AST.Visitor.Visitor;
import Exceptions.WrongParamsException;
import lab7.AbstractNode;
import org.json.simple.JSONArray;

public class FunctionCallNode extends AbstractNode {
    public AbstractNode id;
    public AbstractNode params;

    public JSONArray paramList = new JSONArray();

    public  FunctionCallNode(AbstractNode id, AbstractNode params) {
        this.id = id;
        this.params = params;

        node.put("type", "FunctionCall");
        node.put("id", id.node);
        node.put("params", paramList);
        addParamsToList(params.getFirst());
    }

    public  FunctionCallNode(AbstractNode id) {
        this.id = id;

        node.put("type", "FunctionCall");
        node.put("id", id.node);
    }

    public  void addParamsToList (AbstractNode n) {
        paramList.add(n.node);
        if(n.getSib() != null) addParamsToList(n.getSib());
    }

    @Override
    public void accept(Visitor analyzer) {
        try {
            analyzer.visitFunctionCall(this);
        } catch (WrongParamsException e) {
            System.out.print(e + "\n");
            System.exit(0);

        }
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        try {
            analyzer.visitFunctionCall(this,parent);
        } catch (WrongParamsException e) {
            e.printStackTrace();
            System.exit(0);

        }
    }
}
