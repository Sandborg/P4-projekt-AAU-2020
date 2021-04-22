import AST.*;
import AST.Visitor.Visitor;
import lab7.AbstractNode;

public class Analyzer implements Visitor {
    private SymbolTable top;

    public Analyzer (ProgramNode program, SymbolTable table) {
        top = table;
        program.accept(this);
    }

    public Analyzer (VariableDeclarationNode program, SymbolTable table) {
        top = table;
        program.accept(this);
    }

    @Override
    public void visit(VariableDeclarationNode n) {
        top.put(n.id.getName(),n);
        if(n.body != null) {
            n.body.accept(this, n);
        }
        n.id.accept(this);
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(VariableDeclarationNode n, AbstractNode parent) {

    }

    @Override
    public void visit(IdentifierNode n) {

        if(top.get(n.getName()) == null) System.out.println(n.getName() + " is not declared");
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(IdentifierNode n, AbstractNode parent) {

        if(top.get(n.getName()) == null) System.out.println(n.getName() + " is not declared");
        if(!CheckType(n,parent)) System.out.println("type error identifier");
        if(n.getSib() != null) n.getSib().accept(this, parent);
    }

    @Override
    public void visit(ProgramNode n) {
        n.decls.getFirst().accept(this);
    }

    @Override
    public void visit(ProgramNode n, AbstractNode parent) {

    }

    @Override
    public void visit(BinaryOPNode n) {
        n.number1.accept(this);
        n.number2.accept(this);
            //Make sure that number1 and number2 is correct type :i
            if(!(n.number1 instanceof BinaryOPNode) && !(n.number2 instanceof BinaryOPNode)) {
                if(!CheckType(n.number2,n.number1)) System.out.println("Type Error at binopnode number 1");
            }
        if(n.getSib() != null)
            n.getSib().accept(this);
    }

    public void visit(BinaryOPNode n,AbstractNode parent) {
        n.number1.accept(this,parent);
        n.number2.accept(this,parent);

        if(n.getSib() != null)
            n.getSib().accept(this,parent);
    }

    @Override
    public void visit(TypeNode n) {
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(TypeNode n, AbstractNode parent) {

    }

    @Override
    public void visit(SingleNode n) {
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visit(SingleNode n, AbstractNode parent) {

    }

    @Override
    public void visit(IntegerNode n) {
        if(n.getSib() != null) n.getSib().accept(this);
    }
    @Override
    public void visit(IntegerNode n, AbstractNode parent) {
        if(!CheckType(n,parent)) System.out.println("Type error Integer");
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(FloatNode n) {
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(FloatNode n, AbstractNode parent) {
        if(!CheckType(n,parent)) System.out.println("Type error decimal");
        if(n.getSib() != null) n.getSib().accept(this);
    }
    @Override
    public void visit(AssignmentNode n) {
        n.to.accept(this, n.set);

        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(AssignmentNode n, AbstractNode parent) {

    }

    @Override
    public void visit(FunctionDecNode n) {
        top.put(n.id.getName(),n);
        n.id.accept(this);

        SymbolTable thisScope = new SymbolTable(top);

        top.put(n.id.getName(), thisScope);
        Analyzer ana = new Analyzer((VariableDeclarationNode)n.params.getFirst(), thisScope);
        //n.params.accept(this);
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visit(FunctionDecNode n, AbstractNode parent) {

    }

    public boolean CheckType (AbstractNode n1, AbstractNode n2) {
        String n1Type = "1";
        String n2Type = "2";

        if(n1 instanceof VariableDeclarationNode) n1Type = n1.getName();
        if(n2 instanceof VariableDeclarationNode) n2Type = n2.getName();
        if(n1 instanceof IdentifierNode) n1Type = top.get(n1.getName()).getName();
        if(n2 instanceof IdentifierNode) n2Type = top.get(n2.getName()).getName();
        if(n1 instanceof IntegerNode || n1 instanceof FloatNode) n1Type = n1.getName();
        if(n2 instanceof IntegerNode || n2 instanceof FloatNode) n2Type = n2.getName();

        //System.out.println(n1Type + " " + n2Type);

        if(n1Type == n2Type) return true;
        else return false;
    }
}