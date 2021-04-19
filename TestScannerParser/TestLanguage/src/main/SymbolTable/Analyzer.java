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
        n.body.accept(this);

        top.put(n.id.getName(),n);
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visit(IdentifierNode n) {
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(ProgramNode n) {
        n.decls.getFirst().accept(this);
    }

    @Override
    public void visit(BinaryOPNode n) {
        n.number1.accept(this);
        n.number2.accept(this);
        AbstractNode parent = n.getFirst().getParent();
        if(parent != null && parent instanceof VariableDeclarationNode) {
            VariableDeclarationNode i = (VariableDeclarationNode)parent;
            //Make sure that number1 and number2 is correct type :i
            if(i.type.getName() != n.number1.getName()) System.out.println("Cant assign " + i.type.getName() + " to " + n.number1.getName());
            if(i.type.getName() != n.number2.getName()) System.out.println("Cant assign " + i.type.getName() + " to " + n.number2.getName());
        }


        if(n.number1 instanceof IdentifierNode) {
            if(top.get(n.number1.getName()) == null) System.out.println(n.number1.getName() + " is not declared");
        }
        if(n.number2 instanceof IdentifierNode) {
            if(top.get(n.number2.getName()) == null) System.out.println(n.number2.getName() + " is not declared");
        }
        if(n.getSib() != null) {
            n.getSib().accept(this);
        }

    }

    @Override
    public void visit(TypeNode n) {
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visit(SingleNode n) {
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visit(IntegerNode n) {
        AbstractNode parent = n.getFirst().getParent();
        if(parent != null && parent instanceof VariableDeclarationNode) {
            VariableDeclarationNode i = (VariableDeclarationNode)parent;
            if(i.type.getName() == "decimal") System.out.println("Cant asign int to decimal type");
        }
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visit(FloatNode n) {
        AbstractNode parent = n.getFirst().getParent();
        if(parent != null && parent instanceof VariableDeclarationNode) {
            VariableDeclarationNode i = (VariableDeclarationNode)parent;
            if(i.type.getName() == "int") System.out.println("Cant asign decimal to int type");
        }
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(AssignmentNode n) {
        if(top.get(n.set.getName()) == null) System.out.println(n.set.getName() + " is not declared");
        if(n.to instanceof IdentifierNode) {
            if(top.get(n.to.getName()) == null) System.out.println(n.to.getName() + " is not declared");
        }
        if(n.getSib() != null) n.getSib().accept(this);
    }
}
