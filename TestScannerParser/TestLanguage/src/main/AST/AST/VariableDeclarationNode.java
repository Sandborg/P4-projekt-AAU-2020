package AST;

import AST.Visitor.Visitor;
import Exceptions.VarAlreadyDeclaredException;

public class VariableDeclarationNode extends AbstractNode {
    public AbstractNode type;
    public AbstractNode id;
    public AbstractNode body;

    public Boolean isParameter = false;

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
    }

    public String getType() {return type.getName();};
    public String getName() {return id.getName();};

    public void accept(Visitor v) {
        try {
            v.visitVarDec(this);
        } catch (VarAlreadyDeclaredException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    public void accept(Visitor v, AbstractNode parent) {
        try {
            v.visitVarDec(this, parent);
        } catch (VarAlreadyDeclaredException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

}