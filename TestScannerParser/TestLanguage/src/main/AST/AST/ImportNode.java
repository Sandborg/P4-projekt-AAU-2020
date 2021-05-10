package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class ImportNode extends AbstractNode {
    public AbstractNode name;

    public ImportNode(AbstractNode name) {
        this.name = name;
        node.put("type", "import");
        node.put("name", name.node);
    }

    public void accept(Visitor v) {
        v.visitImportNode(this);
    }
    public void accept(Visitor v, AbstractNode parent) {
        v.visitImportNode(this, parent);
    }
}
