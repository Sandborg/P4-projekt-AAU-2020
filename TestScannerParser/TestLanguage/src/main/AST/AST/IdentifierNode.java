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
    public String getName() {return id;}
    public String getType() {return "Identifier";}
    public String getIdType() {return idType;}
    public void accept(Visitor v) {
        v.visitId(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitId(this, parent);
    }

}