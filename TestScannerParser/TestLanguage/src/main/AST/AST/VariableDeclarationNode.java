package AST;

import AST.Visitor.Visitor;
import lab7.*;
public class VariableDeclarationNode extends AbstractNode {
    public AbstractNode type;
    public AbstractNode id;
    public AbstractNode body;

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
    public void accept(Visitor v) {
        v.visit(this);
    }

}