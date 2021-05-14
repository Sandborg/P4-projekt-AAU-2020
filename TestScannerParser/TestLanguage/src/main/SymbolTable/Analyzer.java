import AST.*;
import AST.Visitor.Visitor;
import Exceptions.*;
import Exceptions.MissingReturnException;
import lab7.AbstractNode;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

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

    public Analyzer (AbstractNode start, SymbolTable table) {
        top = table;
        start.getFirst().accept(this);
    }
    public Analyzer (AbstractNode start, SymbolTable table, AbstractNode parent) {
        top = table;
        start.getFirst().accept(this,parent);
    }

    @Override
    public void visitVarDec(VariableDeclarationNode n) throws VarAlreadyDeclaredException {
        //Insert the variable into the symboltable
        top.put(n.id.getName(),n);

        //If the variable has a body, accept it
        if(n.body != null) {
            if(n.body instanceof IdentifierNode && n.body.getName().equals(n.id.getName())) {
                throw new VarAlreadyDeclaredException("Cant set variable \"" + n.id.getName() + "\" to itself");
            }
            n.body.accept(this, n);
        }

        //Accept it's id
        n.id.accept(this);

        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitVarDec(VariableDeclarationNode n, AbstractNode parent) throws VarAlreadyDeclaredException {
        //Insert the variable into the symboltable
        top.put(n.id.getName(),n);

        //If the variable has a body, accept it
        if(n.body != null) {
            if(n.body instanceof IdentifierNode && n.body.getName() == n.id.getName()) {
                throw new VarAlreadyDeclaredException("Cant set variable \"" + n.id.getName() + "\" to itself");
            }
            n.body.accept(this, n);
        }

        //Accept it's id
        n.id.accept(this);

        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitId(IdentifierNode n) throws VarNotFoundException {
        VariableDeclarationNode vdn = null;
        if(top.get(n.getName()) instanceof VariableDeclarationNode) vdn = (VariableDeclarationNode)top.get((n.getName()));
        n.AddDataType(top.get(n.getName()).getType());

        //Check if the identifer has been declared
        if(top.get(n.getName()) == null) throw new VarNotFoundException(n.getName() + " hasn't been declared");
        if(vdn != null && vdn.isParameter) {
            JSONObject o = new JSONObject();
            o.put("type", "parameterInfo");
            o.put("parameterType", vdn.id.getIdType());
            n.node.put("parameterInfo",o);
        }

        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitId(IdentifierNode n, AbstractNode parent) throws VarNotFoundException, WrongTypeException {
        //Check if the identifer has been declared
        VariableDeclarationNode vdn = null;
        if(top.get(n.getName()) instanceof VariableDeclarationNode) vdn = (VariableDeclarationNode)top.get((n.getName()));
        //Update AST
        n.AddDataType(top.get(n.getName()).getType());
        if(vdn == null)throw new VarNotFoundException(n.getName() + " hasn't been declared");
        //Check if the identifiers type is compatible with the parent

        if (!top.get(n.getName()).getType().equals("string")) {
            if(parent instanceof VariableDeclarationNode) {
                if( !CheckType(n,parent)) throw new WrongTypeException("Can't set " + parent.getType() + " " + parent.getName() + " to " + n.getName());
            }else{
                if( !CheckType(n,parent)) throw new WrongTypeException("Can't set " + parent.getName() + " to " + n.getName());
            }
        }

        if(vdn != null && vdn.isParameter) {
            JSONObject o = new JSONObject();
            o.put("type", "parameterInfo");
            o.put("parameterType", vdn.id.getIdType());
            n.node.put("parameterInfo",o);
        }
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitProg(ProgramNode n) {
        //Continue with the first sibling
        n.decls.getFirst().accept(this);
    }

    @Override
    public void visitProg(ProgramNode n, AbstractNode parent) {

    }

    @Override
    public void visitBinaryOP(BinaryOPNode n) {
        //A binarayOP node has two numbers. Accept them:
        n.number1.accept(this);
        n.number2.accept(this);
        //Continue with the next sibling
        if(n.getSib() != null)
            n.getSib().accept(this);
    }

    public void visitBinaryOP(BinaryOPNode n,AbstractNode parent) {
        //A binarayOP node has two numbers. Accept them:
        n.number1.accept(this);
        n.number2.accept(this);

        //Continue with the next sibling
        if(n.getSib() != null)
            n.getSib().accept(this,parent);
    }

    @Override
    public void visitType(TypeNode n) {
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitType(TypeNode n, AbstractNode parent) {

    }

    @Override
    public void visitInt(IntegerNode n) {
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }
    @Override
    public void visitInt(IntegerNode n, AbstractNode parent) throws WrongTypeException {
        //Check if the integer is compatible with its parent.
        if(!CheckType(n,parent)) {
            if(parent instanceof VariableDeclarationNode) {
                VariableDeclarationNode vn = (VariableDeclarationNode)parent;
                throw new WrongTypeException("Can't declare \"" + parent.getType() + " " + parent.getName() + "\" to int");
            }else {
                throw new WrongTypeException("Can't set \"" + parent.getName() + "\" to int");
            }
        }
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitFloat(FloatNode n) {
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFloat(FloatNode n, AbstractNode parent) throws WrongTypeException {
        //Check if the float is compatible with its parent.
        if(!CheckType(n,parent)) {
            if(parent instanceof VariableDeclarationNode) {
                VariableDeclarationNode vn = (VariableDeclarationNode)parent;
                throw new WrongTypeException("Can't declare \"" + parent.getType() + " " + parent.getName() + "\" to decimal");
            }else {
                throw new WrongTypeException("Can't set \"" + parent.getName() + "\" to decimal");
            }
        }
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }
    @Override
    public void visitAssign(AssignmentNode n) throws PointerException {

        IdentifierNode set = (IdentifierNode)n.set;

        if(n.set.getIdType() == "adr" ) {
            if(n.to instanceof IdentifierNode) {
                if(n.to.getIdType() != "adr") {
                    IdentifierNode to = (IdentifierNode)n.to;
                    throw new PointerException("Can't set \"" +  set.getName() + ".adr\" to \"" + to.getName() + ".val\"");
                }
            }else{
                throw new PointerException("Can't set \"" +  set.getName() + ".adr\" to a value");
            }
        }else {
            if(n.to instanceof IdentifierNode && n.to.getIdType() == "adr") {
                IdentifierNode to = (IdentifierNode)n.to;
                throw new PointerException("Can't set \"" +  set.getName() + ".val\" to \"" + to.getName() + ".adr\"" );
            }
        }
        VariableDeclarationNode vd = (VariableDeclarationNode)top.get(n.set.getName());
        vd.lastAssign = n;



        n.set.accept(this);
        n.to.accept(this, n.set);

        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitAssign(AssignmentNode n, AbstractNode parent) throws PointerException {
        IdentifierNode set = (IdentifierNode)n.set;

        if(n.set.getIdType() == "adr" ) {
            if(n.to instanceof IdentifierNode) {
                if(n.to.getIdType() != "adr") {
                    IdentifierNode to = (IdentifierNode)n.to;
                    throw new PointerException("Can't set \"" +  set.getName() + ".adr\" to \"" + to.getName() + ".val\"");
                }
            }else{
                throw new PointerException("Can't set \"" +  set.getName() + ".adr\" to a value");
            }
        }else {
            if(n.to instanceof IdentifierNode && n.to.getIdType() == "adr") {
                IdentifierNode to = (IdentifierNode)n.to;
                throw new PointerException("Can't set \"" +  set.getName() + ".val\" to \"" + to.getName() + ".adr\"" );
            }
        }
        VariableDeclarationNode vd = (VariableDeclarationNode)top.get(n.set.getName());
        vd.lastAssign = n;
        n.set.accept(this);
        n.to.accept(this, n.set);


        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitFuncDec(FunctionDecNode n) {
        //Put the function name into the global scope.
        top.put(n.id.getName(),n);
        //Check if there already exist a function with that name in the global scope.
        n.id.accept(this);
        //Create a new scope for the new function
        SymbolTable thisScope = new SymbolTable(top);

        //Insert the new scope, into the list of all scopes.
        top.put(n.id.getName(), thisScope);
        //Insert the paremeters into the new scope.
        if(n.params != null) {
            Analyzer ana = new Analyzer((VariableDeclarationNode) n.params.getFirst(), thisScope);
            addParameterToVarDec((VariableDeclarationNode)n.params.getFirst());
        }
        //Continue with next sibling.
        if(n.getSib() != null) n.getSib().accept(this);
    }

    public void addParameterToVarDec (VariableDeclarationNode n) {
        n.isParameter = true;
        if(n.getSib() != null) addParameterToVarDec((VariableDeclarationNode)n.getSib());
    }


    @Override
    public void visitFuncDec(FunctionDecNode n, AbstractNode parent) {
        //This is never used
        System.out.println("weird :shrug: tillykke med at have fundet dette easteregg xD");
    }

    @Override
    public void visitFuncDef(FunctionDefNode n) throws MissingReturnException, WrongTypeException, WrongParamsException {
        n.id.accept(this);

        SymbolTable thisSymbolTable = top.scopes.get(n.id.getName());

        if(!CheckType(n, top.get(n.id.getName()))) throw new WrongTypeException("Function definition \"" + n.id.getName() + "\" has type \"" + n.getType() + "\" but doesn't match prototype");
        if(n.params != null) {
            if (!CheckParams((VariableDeclarationNode) n.params.getFirst(), thisSymbolTable, GetNumberOfSiblings(n.params.getFirst(), 1)))
                throw new WrongParamsException("Function definition \"" + n.id.getName() + "\" parameters doesn't match prototype parameters");
        }
        if(n.body != null) {
            if(!n.getType().equals("void")) {
                if(!LookForReturnStmt(n.body.getFirst())) throw new MissingReturnException("Missing return stmt");
            }
            Analyzer analyzer = new Analyzer(n.body.getFirst(), thisSymbolTable,n);
        }else {
            throw new MissingReturnException("Missing return stmt");
        }
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFuncDef(FunctionDefNode n, AbstractNode parent) {
        //This is for functions inside functions
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitReturnStatement(ReturnStatementNode n) {
        n.argument.accept(this);
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visitReturnStatement(ReturnStatementNode n, AbstractNode parent) throws WrongTypeException {
        n.argument.accept(this,parent);
        if(!CheckType(n.argument,parent)) throw new WrongTypeException("Incorrect return type");
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitFunctionCall(FunctionCallNode n) throws WrongParamsException {
        n.id.accept(this);
        SymbolTable thisSymbolTable = top.scopes.get(n.id.getName());

        //Find prototype node
        FunctionDecNode functionDecNode = (FunctionDecNode)top.get(n.id.getName());
        if(n.params != null) {
            if (!CheckCallParams(n.params.getFirst(), (VariableDeclarationNode) functionDecNode.params.getFirst(), thisSymbolTable))
                throw new WrongParamsException("Parameters in function call \"" + n.id.getName() + "\" doesn't match it's prototype");
        }else {
            if(n.params == null && functionDecNode.params != null) {
                throw new WrongParamsException("Parameters in function call \"" + n.id.getName() + "\" doesn't match it's prototype");
            }
        }
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFunctionCall(FunctionCallNode n, AbstractNode parent) throws WrongParamsException {
        n.id.accept(this);
        if(top.prev != null) {
            FunctionDecNode functionDecNode = (FunctionDecNode) top.prev.get(n.id.getName());
            if (n.params != null) {
                 n.params.getFirst().accept(this);
                if (!CheckCallParams(n.params.getFirst(), (VariableDeclarationNode) functionDecNode.params.getFirst(), top))
                    throw new WrongParamsException("Parameters in function call \"" + n.id.getName() + "\" doesn't match it's prototype");
            } else {
                if (n.params == null && functionDecNode.params != null) {
                    throw new WrongParamsException("Parameters in function call \"" + n.id.getName() + "\" doesn't match it's prototype");
                }
            }
        }
        if(n.getSib() != null) n.getSib().accept(this,parent);

    }

    @Override
    public void visitIfStatement(IfStatementNode n) {
        n.test.getFirst().accept(this);
        n.ifBody.getFirst().accept(this);
        if(n.elseBody != null) n.elseBody.getFirst().accept(this);
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visitIfStatement(IfStatementNode n, AbstractNode parent) {
        n.test.getFirst().accept(this,parent);
        n.ifBody.getFirst().accept(this, parent);
        if(n.elseBody != null) n.elseBody.getFirst().accept(this,parent);
        if(n.getSib() != null) n.getSib().accept(this, parent);
    }

    @Override
    public void visitConditionNode(ConditionNode n) {
        n.left.getFirst().accept(this);
        n.right.getFirst().accept(this);
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitConditionNode(ConditionNode n, AbstractNode parent) {
        n.left.getFirst().accept(this, parent);
        n.right.getFirst().accept(this, parent);
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitStringNode(StringNode n) {
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitStringNode(StringNode n, AbstractNode parent) throws WrongTypeException {
        if(!CheckType(n,parent)){
            if(parent instanceof VariableDeclarationNode) {
                VariableDeclarationNode vn = (VariableDeclarationNode)parent;
                throw new WrongTypeException("Can't declare \"" + parent.getType() + " " + parent.getName() + "\" to string");
            }else {
                throw new WrongTypeException("Can't set \"" + parent.getName() + "\" to string");
            }
        }
        if(n.getSib() != null) n.getSib().accept(this, parent);
    }

    @Override
    public void visitImportNode(ImportNode n) {
        String filename = n.name.getValueString();
        parser p = null;
        try {
            p = new parser(new Scanner(new FileReader("src/main/resources/Libraries/" + filename)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            p.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Analyzer analyzer = new Analyzer((ProgramNode)p.action_obj.prog,top);
        p.action_obj.WriteAST(n.name.getValueString() + ".json");
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitImportNode(ImportNode n, AbstractNode parent) {
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitForLoopNode(ForLoopNode n) {
        n.initCase.accept(this);
        n.testCase.accept(this);
        n.continueCase.accept(this);
        n.body.getFirst().accept(this);
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitForLoopNode(ForLoopNode n, AbstractNode parent) {
        n.initCase.accept(this,parent);
        n.testCase.accept(this,parent);
        n.continueCase.accept(this,parent);
        n.body.getFirst().accept(this,parent);
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    public boolean CheckCallParams(AbstractNode callNode, VariableDeclarationNode decNode, SymbolTable s) {
        //Check if the call has correct number of parameters.
        if(GetNumberOfSiblings(callNode,1) != GetNumberOfSiblings(decNode, 1)) return false;

        //If curr param is identifier, check if it exists
        if(callNode.getType() == "Identifier" && s.get(callNode.getName()) == null) return false;

        callNode.accept(this);
        //Check if correct type
        if(decNode.getType() != "string") {
            if (!CheckType(callNode, decNode)) return false;
        }

        //Check for next param
        if(callNode.getSib() != null) return CheckCallParams(callNode.getSib(), (VariableDeclarationNode) decNode.getSib(), s);

        return true;
    }

    public boolean CheckParams(VariableDeclarationNode n, SymbolTable s, int numberOfSiblings) {

        //Check if there is the correct number of parameters
        if(s.table.size() != numberOfSiblings) {
            return false;
        }

        //Check if the provided parmams exist in the prototype :)
        if(s.get(n.id.getName()) != null) {
            //Check if correct type
            if(n.getType() != "string") {
                if (!CheckType(n, s.get(n.id.getName()))) return false;
            }
            if(n.getSib() != null) return CheckParams((VariableDeclarationNode)n.getSib(),s,numberOfSiblings);
        }else return false;

        return true;
    }

    public Boolean LookForReturnStmt(AbstractNode n) {
        if(n instanceof ReturnStatementNode) return true;
        if(n instanceof IfStatementNode) {
            IfStatementNode in = (IfStatementNode)n;
            if(in.elseBody != null) {
                if(LookForReturnStmt(in.ifBody) && LookForReturnStmt(in.elseBody)) return true;
            }
        }
        if(n instanceof ForLoopNode) {
            ForLoopNode fn = (ForLoopNode)n;
            if(LookForReturnStmt(fn.body)) return true;
        }

        if(n.getSib() != null) return LookForReturnStmt(n.getSib());
        else return false;
    }

    public int GetNumberOfSiblings(AbstractNode n, int number) {
        if(n.getSib() != null) return GetNumberOfSiblings(n.getSib(), number + 1);
        else return number;
    }
    //This function checks if two type are compatable with each other.
    //Returns true if yes, false if not.
    public boolean CheckType (AbstractNode n1, AbstractNode n2) {
        String n1Type = "1";
        String n2Type = "2";

        //Get the type of n1 and n2:
        if(n1 instanceof VariableDeclarationNode) n1Type = n1.getType();
        if(n2 instanceof VariableDeclarationNode) n2Type = n2.getType();
        if(n1 instanceof IdentifierNode) n1Type = top.get(n1.getName()).getType();
        if(n2 instanceof IdentifierNode) n2Type = top.get(n2.getName()).getType();
        if(n1 instanceof IntegerNode || n1 instanceof FloatNode || n1 instanceof StringNode) n1Type = n1.getName();
        if(n2 instanceof IntegerNode || n2 instanceof FloatNode ||n2 instanceof StringNode) n2Type = n2.getName();
        if(n1 instanceof FunctionDecNode || n1 instanceof FunctionDefNode) n1Type = n1.getType();
        if(n2 instanceof FunctionDecNode || n2 instanceof FunctionDefNode) n2Type = n2.getType();

        if(n1 instanceof BinaryOPNode) {
            boolean test1 = CheckType(((BinaryOPNode) n1).number1,n2);
            boolean test2 = CheckType(((BinaryOPNode) n1).number2,n2);
            return (test1 && test2);
        }
        if(n2 instanceof BinaryOPNode) {
            boolean test1 = CheckType(((BinaryOPNode) n2).number1,n1);
            boolean test2 = CheckType(((BinaryOPNode) n2).number2,n1);
            return (test1 && test2);
        }


        //Check if the types are equal
        if(n2Type != "string") return n1Type == n2Type;
        else return true;

    }
}