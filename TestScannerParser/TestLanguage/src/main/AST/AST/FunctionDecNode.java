package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONArray;

public class FunctionDecNode extends AbstractNode{
    public AbstractNode type;
    public AbstractNode id;
    public AbstractNode params;

    public JSONArray paramList = new JSONArray();
    public FunctionDecNode (AbstractNode type, AbstractNode id, AbstractNode params) {
        this.type = type;
        this.id = id;
        this.params = params;

        node.put("Type", "FunctionDeclaration");
        node.put("Id", id.node);
        addParamsToList(params.getFirst());
        node.put("Params", paramList);

    }
    public  void addParamsToList (AbstractNode n) {
        paramList.add(n.node);
        if(n.getSib() != null) addParamsToList(n.getSib());
    }

    @Override
    public void accept(Visitor analyzer) {
        analyzer.visitFuncDec(this);
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        analyzer.visitFuncDec(this,parent);
    }
}