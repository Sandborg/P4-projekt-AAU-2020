import AST.*;
import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONObject;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
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
    public void visitVarDec(VariableDeclarationNode n) {
        //Insert the variable into the symboltable
        top.put(n.id.getName(),n);

        //If the variable has a body, accept it
        if(n.body != null) {
            if(n.body instanceof IdentifierNode && n.body.getName().equals(n.id.getName())) {
                System.out.println("Cant declare a variable to itself");
            }
            n.body.accept(this, n);
        }

        //Accept it's id
        n.id.accept(this);
        if(n.body instanceof BinaryOPNode && n.getName().equals("string")) {
            n.UpdateInitNode(CreateStringFromBinaryOpNode(n.body,""));
        }
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitVarDec(VariableDeclarationNode n, AbstractNode parent) {
        //Insert the variable into the symboltable
        top.put(n.id.getName(),n);

        //If the variable has a body, accept it
        if(n.body != null) {
            if(n.body instanceof IdentifierNode && n.body.getName() == n.id.getName()) {
                System.out.println("Cant declare a variable to itself");
            }

            if(n.body instanceof BinaryOPNode && n.getName().equals("string")) {
                n.UpdateInitNode(CreateStringFromBinaryOpNode(n.body,""));
            }

            n.body.accept(this, n);
        }

        //Accept it's id
        n.id.accept(this);

        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this,parent);
    }

    @Override
    public void visitId(IdentifierNode n) {
        VariableDeclarationNode vdn = null;
        if(top.get(n.getName()) instanceof VariableDeclarationNode) vdn = (VariableDeclarationNode)top.get((n.getName()));

        n.AddDataType(top.get(n.getName()).getName());
        //Check if the identifer has been declared
        if(top.get(n.getName()) == null) System.out.println(n.getName() + " is not declared");
        if(vdn != null && vdn.isParameter) {
            JSONObject o = new JSONObject();
            o.put("type", "parameterInfo");
            o.put("parameterType", vdn.id.getIdType());
            n.node.put("parameterInfo",o);
        }

        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitId(IdentifierNode n, AbstractNode parent) {
        //Check if the identifer has been declared
        VariableDeclarationNode vdn = null;
        if(top.get(n.getName()) instanceof VariableDeclarationNode) vdn = (VariableDeclarationNode)top.get((n.getName()));
        if(vdn == null) System.out.println(n.getName() + " is not declared");
        //Check if the identifiers type is compatible with the parent:
        if(!CheckType(n,parent)) System.out.println("type error identifier");
        //Update AST
        n.AddDataType(top.get(n.getName()).getName());
        if(vdn != null && vdn.isParameter) {
            JSONObject o = new JSONObject();
            o.put("type", "parameterInfo");
            o.put("parameterType", vdn.id.getIdType());
            n.node.put("parameterInfo",o);
        }
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this, parent);
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
        n.number1.accept(this,parent);
        n.number2.accept(this,parent);

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
    public void visitInt(IntegerNode n, AbstractNode parent) {
        //Check if the integer is compatible with its parent.
        if(!CheckType(n,parent)) System.out.println("Type error Integer");
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFloat(FloatNode n) {
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFloat(FloatNode n, AbstractNode parent) {
        //Check if the float is compatible with its parent.
        if(!CheckType(n,parent)) System.out.println("Type error decimal");
        //Continue with the next sibling
        if(n.getSib() != null) n.getSib().accept(this);
    }
    @Override
    public void visitAssign(AssignmentNode n) {
        if(n.set.getIdType() == "adr" ) {
            if(n.to instanceof IdentifierNode) {
                if(n.to.getIdType() != "adr") {
                    System.out.println("adr must be set to another adr");
                }
            }else{
                System.out.println("adr must be set to another adr");
            }
        }
        VariableDeclarationNode vd = (VariableDeclarationNode)top.get(n.set.getName());
        vd.lastAssign = n;
        if(n.to instanceof BinaryOPNode && top.get(n.set.getName()).getName().equals("string")) {
            n.UpdateRightNode(CreateStringFromBinaryOpNode(n.to,""));
        }

        n.set.accept(this);
        n.to.accept(this, n.set);

        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitAssign(AssignmentNode n, AbstractNode parent) {
        n.set.accept(this);
        n.to.accept(this, n.set);

        VariableDeclarationNode vd = (VariableDeclarationNode)top.get(n.set.getName());
        vd.lastAssign = n;

        if(n.to instanceof BinaryOPNode && top.get(n.set.getName()).getName().equals("string")) {
            n.UpdateRightNode(CreateStringFromBinaryOpNode(n.to,""));
        }
        if(n.getSib() != null) n.getSib().accept(this);
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
        System.out.println("weird");
    }

    @Override
    public void visitFuncDef(FunctionDefNode n) {
        n.id.accept(this);

        SymbolTable thisSymbolTable = top.scopes.get(n.id.getName());

        if(!CheckType(n, top.get(n.id.getName()))) System.out.println("Type error func def");
        if(n.params != null) {
            if (!CheckParams((VariableDeclarationNode) n.params.getFirst(), thisSymbolTable, GetNumberOfSiblings(n.params.getFirst(), 1)))
                System.out.println("Wrong params");
        }
        if(n.body != null) {
            Analyzer analyzer = new Analyzer(n.body.getFirst(), thisSymbolTable,n);
        }
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFuncDef(FunctionDefNode n, AbstractNode parent) {
        //This is for functions inside functions
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitReturnStatement(ReturnStatementNode n) {

        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visitReturnStatement(ReturnStatementNode n, AbstractNode parent) {
        if(!CheckType(n.argument,parent)) System.out.println("Wrong return type");
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFunctionCall(FunctionCallNode n) {
        n.id.accept(this);
        SymbolTable thisSymbolTable = top.scopes.get(n.id.getName());

        //Find prototype node
        FunctionDecNode functionDecNode = (FunctionDecNode)top.get(n.id.getName());
        if(n.params != null) {
            if (!CheckCallParams(n.params.getFirst(), (VariableDeclarationNode) functionDecNode.params.getFirst(), thisSymbolTable))
                System.out.println("Wrong call parameters");
        }else {
            if(n.params == null && functionDecNode.params != null) {
                System.out.println("Wrong call parameters");
            }
        }
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitFunctionCall(FunctionCallNode n, AbstractNode parent) {
        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitIfStatement(IfStatementNode n) {
        n.test.getFirst().accept(this);
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visitIfStatement(IfStatementNode n, AbstractNode parent) {
        n.test.getFirst().accept(this,parent);
        if(n.getSib() != null) n.getSib().accept(this);

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
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visitStringNode(StringNode n) {
        if(n.getSib() != null) n.getSib().accept(this);

    }

    @Override
    public void visitStringNode(StringNode n, AbstractNode parent) {
        if(n.getSib() != null) n.getSib().accept(this);

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

        if(n.getSib() != null) n.getSib().accept(this);
    }

    @Override
    public void visitImportNode(ImportNode n, AbstractNode parent) {
        if(n.getSib() != null) n.getSib().accept(this);
    }

    public boolean CheckCallParams(AbstractNode callNode, VariableDeclarationNode decNode, SymbolTable s) {
        //Check if the call has correct number of parameters.
        if(GetNumberOfSiblings(callNode,1) != GetNumberOfSiblings(decNode, 1)) return false;

        //If curr param is identifier, check if it exists
        if(callNode.getType() == "Identifier" && s.get(callNode.getName()) == null) return false;

        //Check if correct type
        if(!CheckType(callNode,decNode)) return false;

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
            if(!CheckType(n,s.get(n.id.getName()))) return false;
            if(n.getSib() != null) return CheckParams((VariableDeclarationNode)n.getSib(),s,numberOfSiblings);
        }else return false;

        return true;
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
        if(n1 instanceof VariableDeclarationNode) n1Type = n1.getName();
        if(n2 instanceof VariableDeclarationNode) n2Type = n2.getName();
        if(n1 instanceof IdentifierNode) n1Type = top.get(n1.getName()).getName();
        if(n2 instanceof IdentifierNode) n2Type = top.get(n2.getName()).getName();
        if(n1 instanceof IntegerNode || n1 instanceof FloatNode) n1Type = n1.getName();
        if(n2 instanceof IntegerNode || n2 instanceof FloatNode) n2Type = n2.getName();
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
        if(n1Type == n2Type) return true;
        else return false;
    }

    public String CreateStringFromBinaryOpNode (AbstractNode n, String s) {
        if(n instanceof BinaryOPNode) {
            BinaryOPNode bn = (BinaryOPNode)n;
                s+= CreateStringFromBinaryOpNode(bn.number1, "");
                s+= CreateStringFromBinaryOpNode(bn.number2, "");

            return s;
        }else {
            if(n instanceof IdentifierNode) {
                VariableDeclarationNode vd = (VariableDeclarationNode)top.get(n.getName());
                if(vd.lastAssign != null) {
                    if(vd.lastAssign.to instanceof BinaryOPNode) {
                        BinaryOPNode bn = (BinaryOPNode)vd.lastAssign.to;
                        if(!(bn.number1 instanceof StringNode) && !(bn.number2 instanceof StringNode)) {
                            if(vd.getType().equals("int")) {
                                return String.valueOf((int)(bn.result));
                            }else {
                                return String.valueOf(bn.result);
                            }
                        }
                    }
                    return CreateStringFromBinaryOpNode(vd.lastAssign.to,"");
                }else{
                    if(vd.body instanceof BinaryOPNode) {
                        BinaryOPNode bn = (BinaryOPNode)vd.body;
                        if(!(bn.number1 instanceof StringNode) && !(bn.number2 instanceof StringNode)) {
                            if(vd.getType().equals("int")) {
                                return String.valueOf((int)(bn.result));
                            }else {
                                return String.valueOf(bn.result);
                            }
                        }
                    }
                    return CreateStringFromBinaryOpNode(vd.body,"");
                    //return vd.body.getValueString();
                }
            }
            return n.getValueString();
        }
    }
}