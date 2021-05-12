package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONArray;

public class FunctionDefNode extends AbstractNode {

    public AbstractNode id;
    public AbstractNode type;
    public AbstractNode params;
    public AbstractNode body;

    public JSONArray paramList = new JSONArray();
    public JSONArray bodyList = new JSONArray();

    public FunctionDefNode(AbstractNode type, AbstractNode id, AbstractNode params, AbstractNode body) {
        this.id = id;
        this.type = type;
        this.params = params;
        this.body =body;

        node.put("type", "FunctionDefinition");
        node.put("dataType", type.node);
        node.put("id", id.node);
        node.put("params", paramList);
        addParamsToList(params.getFirst());
        node.put("body", bodyList);
        addBodyToList (body.getFirst());
    }
    public FunctionDefNode(AbstractNode type, AbstractNode id, String params, AbstractNode body) {
        this.id = id;
        this.type = type;
        this.body =body;

        node.put("type", "FunctionDefinition");
        node.put("dataType", type.node);
        node.put("id", id.node);
        node.put("body", bodyList);
        addBodyToList (body.getFirst());
    }

    public FunctionDefNode(AbstractNode type, AbstractNode id, AbstractNode params) {
        this.id = id;
        this.type = type;
        this.params = params;
        if(params instanceof VariableDeclarationNode) this.params = params;
        else this.body = params;
        node.put("type", "FunctionDefinition");
        node.put("dataType", type.node);
        node.put("id", id.node);
        node.put("params", paramList);
        addParamsToList(params.getFirst());
    }

    public FunctionDefNode(AbstractNode type, AbstractNode id) {
        this.id = id;
        this.type = type;
        this.params = params;
        node.put("type", "FunctionDefinition");
        node.put("dataType", type.node);
        node.put("id", id.node);
    }



    public String getType() {return type.getName();}

    public void addBodyToList (AbstractNode n) {
        bodyList.add(n.node);
        if(n.getSib() != null) addBodyToList(n.getSib());
    }
    public  void addParamsToList (AbstractNode n) {
        paramList.add(n.node);
        if(n.getSib() != null) addParamsToList(n.getSib());
    }

    @Override
    public void accept(Visitor analyzer) {
        analyzer.visitFuncDef(this);
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        analyzer.visitFuncDef(this,parent);
    }
}
