package AST;

import AST.Visitor.Visitor;
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

        node.put("type", "FunctionDeclaration");
        node.put("Id", id.node);
        node.put("FuncType", type.node);
        node.put("Params", paramList);

        addParamsToList(params.getFirst());
    }

    public FunctionDecNode (AbstractNode type, AbstractNode id) {
        this.type = type;
        this.id = id;

        node.put("type", "FunctionDeclaration");
        node.put("Id", id.node);
        node.put("FuncType", type.node);
    }
    public  void addParamsToList (AbstractNode n) {
        paramList.add(n.node);
        if(n.getSib() != null) addParamsToList(n.getSib());
    }

    public String getType() {return type.getName();}

    @Override
    public void accept(Visitor analyzer) {
        analyzer.visitFuncDec(this);
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        analyzer.visitFuncDec(this,parent);
    }
}