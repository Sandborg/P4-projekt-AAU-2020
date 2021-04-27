import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;
public class CodeGenerator {

    public CodeGenerator () throws IOException, ParseException {
        //Create the file:
        FileWriter program = new FileWriter("src/main/CCompiler/tcc/program.c");
        //Write the file
        ReadTree(program);
        //Close input stream
        program.close();
    }

    public void ReadTree(FileWriter program) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader ast = new FileReader("src/main/resources/ast.json");
        JSONArray obj = (JSONArray) parser.parse(ast);
        JSONArray body = new JSONArray();
        for(Object o: obj){
            JSONObject object = (JSONObject) o;
            body = (JSONArray)object.get("Body");
        }
        //Always include the stdio.h libary
        program.write("#include <stdio.h>\n");

        //Then add prototypes
        for(Object o: body) {
            JSONObject thisObject = (JSONObject)o;
            if(thisObject.get("type") != null) {
                if (thisObject.get("type").equals("FunctionDeclaration")) {
                    program.append(GetFunctionDec(thisObject, "") + ";\n");
                }
            }
        }

        //Then add function definitions
        for(Object o: body) {
            JSONObject thisObject = (JSONObject)o;
            if(thisObject.get("type") != null) {
                if (thisObject.get("type").equals("FunctionDefinition")) {
                    program.append(GetFunctionDef(thisObject, "") + "\n");
                }
            }
        }

        //Add main
        program.append("int main() { \n");
        program.append(GetBody(body, ""));

        program.append("\nreturn 0; \n}");
    }

    public String GetBody (JSONArray body, String expr) {
        for(Object o: body) {
            JSONObject thisObject = (JSONObject)o;
            if(thisObject.get("type") != null) {
                if (thisObject.get("type").equals("BinaryExpression")) {
                    expr+=GetBinaryOperator(thisObject, "") + ";\n";
                } else if (thisObject.get("type").equals("int") || thisObject.get("type").equals("decimal")) {
                    expr+=(thisObject.get("value") + ";\n");
                } else if (thisObject.get("type").equals("VariableDeclaration")) {
                    expr+=(GetVariableDeclaration(thisObject, "") + ";\n");
                } else if (thisObject.get("type").equals("AssignmentExpression")) {
                    expr+=(GetAssignmentExpression(thisObject, "") + ";\n");
                } else if(thisObject.get("type").equals("FunctionCall")) {
                    expr+=(GetFunctionCall(thisObject, "") + "; \n");
                }else if(thisObject.get("type").equals("ReturnStatement")) {
                    expr+=(GetReturnStatement(thisObject, "") + "; \n");
                }
            }
            System.out.println(o);
        }
        return expr;
    }

    public String GetReturnStatement(JSONObject o, String expr) {
        JSONObject argument = (JSONObject)o.get("Argument");
        expr+= "return ";
        if(argument.get("type").equals("BinaryExpression")) {
            expr+=GetBinaryOperator(argument,"");
        }else if(argument.get("type").equals("Identifier")) {
            expr+=argument.get("id");
        }
        else {
            expr+=argument.get("value");
        }
        return expr;
    }

    public String GetFunctionCall(JSONObject o, String expr) {
        JSONObject id = (JSONObject)o.get("id");
        JSONArray params = (JSONArray)o.get("params");

        expr+=id.get("id") + "(";
        if(params != null) {
            expr+= GetParameters(params, "") + ")";
        }else expr+=")";
        return expr;
    }

    public String GetFunctionDef(JSONObject o, String expr) {
        JSONObject type = (JSONObject)o.get("dataType");
        JSONObject id = (JSONObject)o.get("id");
        JSONArray params = (JSONArray)o.get("params");
        JSONArray body = (JSONArray)o.get("body");

        expr+=  type.get("dataType") + " " + id.get("id") + " (";
        if(params != null) {
            expr+= GetParameters(params, "") + "){\n";
        }else expr+= "){\n";

        if(body != null) {
            expr +=GetBody(body, "") + "\n}\n";
        }else expr+="\n}\n";

        return expr;
    }

    public String GetFunctionDec(JSONObject o, String expr) {
        JSONObject funcType = (JSONObject)o.get("FuncType");
        JSONObject id = (JSONObject)o.get("Id");
        JSONArray params = (JSONArray)o.get("Params");
        expr +=funcType.get("dataType") + " " + id.get("id") + " (";
        if(params != null){
            expr+=GetParameters((JSONArray)o.get("Params"), "") + ")";
        }
        else{
            expr+= ")";
        }
        return expr;
    }

    public String GetParameters(JSONArray o, String expr) {
        for(Object obj: o) {
            JSONObject thisObject = (JSONObject)obj;
            if(thisObject.get("type").equals("VariableDeclaration")) {
                expr+=GetVariableDeclaration(thisObject, "");
            }else if(thisObject.get("type").equals("BinaryExpression")) {
                expr+=GetBinaryOperator(thisObject,"");
            }else if(thisObject.get("type").equals("Identifier")) {
                expr+=thisObject.get("id");
            }
            else {
                expr+=thisObject.get("value");
            }
            if(o.indexOf(thisObject) != o.size() - 1) {
                expr+=",";
            }
        }
        return expr;
    }

    public String GetAssignmentExpression(JSONObject o, String expr) {
        JSONObject left = (JSONObject)o.get("left");
        JSONObject right = (JSONObject)o.get("right");

        expr+= left.get("id") + " = ";
        if(right.get("type").equals("BinaryExpression")) {
            expr+= GetBinaryOperator(right, "");
        }else if(right.get("type").equals("Identifier")){
            expr+=right.get("id");
        }else{
            expr+=right.get("value");
        }
        return expr;
    }

    public String GetVariableDeclaration(JSONObject o, String expr) {
        JSONObject init = (JSONObject)o.get("Init");
        JSONObject id = (JSONObject)o.get("Identifier");
        JSONObject varType = (JSONObject)o.get("VariableType");
        expr+=varType.get("dataType") + " " + id.get("id");
        if(init != null) {
            if(init.get("type").equals("BinaryExpression")) {
                expr+= " = " + GetBinaryOperator(init,"");
            }else if(init.get("type").equals("Identifier")) {
                expr += " = " + init.get("id");
            }
            else expr+= " = " + init.get("value");
        }
        return expr;
    }

    public String GetBinaryOperator (JSONObject o, String expr) {
        JSONObject left = (JSONObject)o.get("left");
        JSONObject right = (JSONObject)o.get("right");

        if(left.get("type").equals("BinaryExpression")) {
            expr+=GetBinaryOperator(left,"");
        }else{
            expr+=left.get("value");
        }
        expr+=o.get("operator");
        if(right.get("type").equals("BinaryExpression")) {
            expr+=GetBinaryOperator(right,"");
        }else{
            expr+=right.get("value");
        }
        return expr;
    }
}
