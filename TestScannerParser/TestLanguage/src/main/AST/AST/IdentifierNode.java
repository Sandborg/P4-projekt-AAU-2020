package AST;

import AST.Visitor.Visitor;
import lab7.*;

public class IdentifierNode extends AbstractNode {
    public String id;
    public String idType;

    public IdentifierNode (String id, String idType) {
        if(id.contains(".")) {
            this.id = id.split("[.]")[0];
        }else {
            this.id = id;
        }
        this.idType = idType;
        node.put("type", "Identifier");
        node.put("id", this.id);
        node.put("idType", idType);
    }

    public void AddDataType (String t) {
        node.put("dataType", t);
    }
    public String getName() {return id;}
    public String getType() {return "Identifier";}
    public String getIdType() {return idType;}

    @Override
    public void accept(Visitor analyzer) {

    }

    public void accept(Visitor v, AbstractNode type) {
        v.visitId(this, type);
    }
    public void accept(Visitor v, AbstractNode type, AbstractNode parent) {
        v.visitId(this, type, parent);
    }

}