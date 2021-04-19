import AST.*;
import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class Analyzer implements Visitor {
    private SymbolTable top;

    public Analyzer (ProgramNode program, SymbolTable table) {
        top = table;
        program.accept(this);
    }

    @Override
    public void visit(VariableDeclarationNode n) {
        top.put(n.id.getName(),n);
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visit(IdentifierNode n) {

    }

    @Override
    public void visit(ProgramNode n) {
        n.decls.getFirst().accept(this);
    }

    @Override
    public void visit(BinaryOPNode n) {

    }

    @Override
    public void visit(TypeNode n) {

    }

    @Override
    public void visit(SingleNode n) {

    }

    @Override
    public void visit(IntegerNode n) {

    }

    @Override
    public void visit(FloatNode n) {

    }
}
