package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class ReturnStatementNode extends AbstractNode{
    public AbstractNode argument;

    public ReturnStatementNode(AbstractNode argument) {
        this.argument = argument;
        node.put("type", "ReturnStatement");
        node.put("Argument", argument.node);
    }

    @Override
    public void accept(Visitor analyzer) {
        analyzer.visitReturnStatement(this);
    }

    @Override
    public void accept(Visitor analyzer, AbstractNode parent) {
        analyzer.visitReturnStatement(this, parent);
    }
}
