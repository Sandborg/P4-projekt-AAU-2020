package AST;

import AST.Visitor.Visitor;

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
        try {
            analyzer.visitReturnStatement(this, parent);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
