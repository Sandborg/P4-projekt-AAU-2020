package AST;

import AST.Visitor.Visitor;
import lab7.*;
import org.json.simple.JSONObject;

public class VariableDeclarationNode extends AbstractNode {
    public AbstractNode type;
    public AbstractNode id;
    public AbstractNode body;

    public AssignmentNode lastAssign;
    public Boolean isParameter = false;
    public String dataType;
    public VariableDeclarationNode (AbstractNode type, AbstractNode id, AbstractNode body) {
        this.type = type;
        this.id = id;
        this.body = body;


        node.put("type", "VariableDeclaration");
        node.put("Identifier", id.node);
        node.put("VariableType", type.node);
        if(body != null) {
            node.put("Init", body.node);
        }
    }


    public VariableDeclarationNode (AbstractNode type, AbstractNode id) {
        this.type = type;
        this.id = id;


        node.put("type", "VariableDeclaration");
        node.put("Identifier", id.node);
        node.put("VariableType", type.node);
        if(body != null) {
            node.put("Init", body.node);
        }
    }

    public void UpdateInitNode(String s) {
        JSONObject o = new JSONObject();
        o.put("type", "string");
        o.put("value", "\"" + s + "\"");
        node.put("Init", o);
    }

    public String getType() {return type.getName();};
    public String getName() {return type.getName();};
    public void accept(Visitor v) {
        v.visitVarDec(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitVarDec(this, parent);
    }

}