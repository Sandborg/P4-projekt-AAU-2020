package AST;

import AST.Visitor.Visitor;

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

    public void accept(Visitor v) {
        try {
            v.visitId(this);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public void accept(Visitor v, AbstractNode parent) {
        try {
            v.visitId(this, parent);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}