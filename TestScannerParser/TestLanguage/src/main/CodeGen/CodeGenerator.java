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
        program.append(GetBody(body, "", false, null));
        program.append("\nreturn 0; \n}");
    }

    public String GetBody (JSONArray body, String expr, Boolean insideFunction, JSONArray params) {
        for(Object o: body) {
            JSONObject thisObject = (JSONObject)o;
            if(thisObject.get("type") != null) {
                if (thisObject.get("type").equals("BinaryExpression")) {
                    expr+=GetBinaryOperator(thisObject, "") + ";\n";
                } else if (thisObject.get("type").equals("int") || thisObject.get("type").equals("decimal")) {
                    expr+=(thisObject.get("value") + ";\n");
                } else if (thisObject.get("type").equals("VariableDeclaration")) {
                    expr+=(GetVariableDeclaration(thisObject, "", true, insideFunction) + ";\n");
                } else if (thisObject.get("type").equals("AssignmentExpression")) {
                    expr+=(GetAssignmentExpression(thisObject, "", insideFunction, params) + ";\n");
                } else if(thisObject.get("type").equals("FunctionCall")) {
                    expr+=(GetFunctionCall(thisObject, "") + "; \n");
                }else if(thisObject.get("type").equals("ReturnStatement")) {
                    expr+=(GetReturnStatement(thisObject, "") + "; \n");
                }
            }
            //System.out.println(o);
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

        expr  += GetBodyParams(params, "");
        if(body != null) {
            expr  += GetBody(body, "", true, params) + "\n}\n";
        }else expr+="\n}\n";

        return expr;
    }

    public String GetBodyParams (JSONArray o, String expr) {
        for(Object obj: o) {
            JSONObject thisObject = (JSONObject)obj;

            JSONObject id = (JSONObject)thisObject.get("Identifier");
            JSONObject varType = (JSONObject)thisObject.get("VariableType");
            if(!id.get("idType").equals("adr")) {
                expr += varType.get("dataType") + " *" + id.get("id") + "p = &" + id.get("id") + ";\n";
            }
        }

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
                expr+=GetVariableDeclarationParameter(thisObject, "" );
            }else if(thisObject.get("type").equals("BinaryExpression")) {
                expr+=GetBinaryOperator(thisObject,"");
            }else if(thisObject.get("type").equals("Identifier")) {
                if(thisObject.get("idType").equals("adr")) {
                    expr+= "&" + thisObject.get("id") + "p";
                }else{
                    expr+= "*" + thisObject.get("id") + "p";
                }
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

    public String GetVariableDeclarationParameter (JSONObject o, String expr) {
        JSONObject init = (JSONObject)o.get("Init");
        JSONObject id = (JSONObject)o.get("Identifier");
        JSONObject varType = (JSONObject)o.get("VariableType");

        if(id.get("idType").equals("adr")) {
            expr+= varType.get("dataType") + " **" + id.get("id");
        }else {
            expr+= varType.get("dataType") + " " + id.get("id");
        }
        return expr;
    }

    public String GetAssignmentExpression(JSONObject o, String expr, Boolean insideFunction, JSONArray params) {
        JSONObject left = (JSONObject)o.get("left");
        JSONObject right = (JSONObject)o.get("right");

        //First we make left side of the assignment:
        //Check if the assignment is inside a function
        if(insideFunction) {
            //Check if the left identifier is from the parameters
            if(CheckParam(params,left)) {
                //Check if the parameter identifier is adr
                if(GetParamIdType(params,left).equals("adr")) {
                    //Check if the current identifier is adr
                    if(left.get("idType").equals("adr")) {
                        expr += "*" + left.get("id") + " =";
                    }else{
                        expr += "**" + left.get("id") + " =";
                    }
                    //If the left parameter identifier is of type "val"
                }else {
                    if(left.get("idType").equals("adr")) {
                        expr += left.get("id") + "p = ";
                    }else{
                        expr += "*" + left.get("id") + "p = ";
                    }
                }
                //If the left parameter isn't from parameters:
            }else {
                if(left.get("idType").equals("adr")) {
                    expr += left.get("id") + "p = ";
                }else{
                    expr += "*" + left.get("id") + "p = ";
                }
            }
            //If the assignment isn't inside a function
        }else{
            if(left.get("idType").equals("adr")) {
                expr += left.get("id") + "p = ";
            }else{
                expr += "*" + left.get("id") + "p = ";
            }
        }

        //Make right side of assignment
        if(right.get("type").equals("BinaryExpression")) {
            expr+= GetBinaryOperator(right, "");
        }else if(right.get("type").equals("Identifier")){
            //If the assignment is inside a function
            if(insideFunction) {
                //If the right identifier comes from the parameters
                if(CheckParam(params,right)) {
                    //If the parameter identifier is of type "adr"
                    if(GetParamIdType(params,right).equals("adr")) {
                        //If the current identifier is of type "adr"
                        if(right.get("idType").equals("adr")) {
                            expr += "*" + right.get("id") + "";
                        }else{
                            expr += "**" + right.get("id") + "";
                        }
                        //If the parameter identifier is of type "val"
                    }else {
                        if(right.get("idType").equals("adr")) {
                            expr += right.get("id") + "p";
                        }else{
                            expr += "*" + right.get("id") + "p";
                        }
                    }
                    //If the right identifier doesn't come from parameters
                }else {
                    if(right.get("idType").equals("adr")) {
                        expr += right.get("id") + "p";
                    }else{
                        expr += "*" + right.get("id") + "p ";
                    }
                }
                //If we are not inside a function
            }else{
                if(right.get("idType").equals("adr")) {
                    expr += right.get("id") + "p";
                }else{
                    expr += "*" + right.get("id") + "p";
                }
            }
        }else{
            expr+=right.get("value");
        }
        return expr;
    }

    public String GetVariableDeclaration(JSONObject o, String expr, Boolean createPointer, Boolean insideFunction) {
        JSONObject init = (JSONObject)o.get("Init");
        JSONObject id = (JSONObject)o.get("Identifier");
        JSONObject varType = (JSONObject)o.get("VariableType");
        if(varType.get("dataType").equals("decimal")) {
            expr+="float " + id.get("id");
        }else{
            expr+=varType.get("dataType") + " " + id.get("id");
        }
        if(init != null) {
            if(init.get("type").equals("BinaryExpression")) {
                expr+= " = " + GetBinaryOperator(init,"");
            }else if(init.get("type").equals("Identifier")) {
                if(insideFunction && id.get("idType").equals("adr")) {
                    expr+= " = **" + init.get("id") + ";";
                }else{
                    if(insideFunction) {
                        expr+= " = *" + init.get("id") + "p;";
                    }else {
                        expr+= " = *" + init.get("id") + ";";
                    }
                }
            }
            else expr+= " = " + init.get("value") +";";
        }
        if(createPointer) {
            if(varType.get("dataType").equals("decimal")) {
                expr += "float *" + id.get("id") + "p = " + "&" + id.get("id");
            }else{
                expr += "\n" + varType.get("dataType") + " *" + id.get("id") + "p = " + "&" + id.get("id");
            }
        }
        return expr;
    }

    public String GetBinaryOperator (JSONObject o, String expr) {
        JSONObject left = (JSONObject)o.get("left");
        JSONObject right = (JSONObject)o.get("right");

        if(left.get("type").equals("BinaryExpression")) {
            expr+=GetBinaryOperator(left,"");
        }else if(left.get("type").equals("Identifier")) {
            if(left.get("idType") == "adr") {
                expr+= "&" + left.get("id") + "p";
            }else{
                expr+= "*" + left.get("id") + "p";
            }
        }else{
            expr+=left.get("value");
        }
        expr+=o.get("operator");

        if(right.get("type").equals("BinaryExpression")) {
            expr+=GetBinaryOperator(right,"");
        }else if(right.get("type").equals("Identifier")) {
            if(right.get("idType") == "adr") {
                expr+= "&" + right.get("id") + "p";
            }else{
                expr+= "*" + right.get("id") + "p";
            }
        }else{
            expr+=right.get("value");
        }
        return expr;
    }

    public String GetParamIdType(JSONArray o, JSONObject n) {
        for(Object obj : o) {
            JSONObject thisObject = (JSONObject)obj;
            JSONObject id = (JSONObject)thisObject.get("Identifier");

            if(id.get("id").equals(n.get("id"))) {
                return (String)id.get("idType");
            }
        }

        return "";
    }

    public Boolean CheckParam(JSONArray o, JSONObject n){
        // go through each object in parameter list.
        for (Object obj : o){
            // Cast to JSONObject, so that we can get the parameter id.
            JSONObject thisObject = (JSONObject)obj;
            JSONObject id = (JSONObject)thisObject.get("Identifier");
            // Check if the correct Id from assignment matches any of the id's in the parameter list.
            // If match is found, return true/false, for whether or not the variable is from another scope
            // than the function definition.
           if(id.get("id").equals(n.get("id"))){
               return true;
           }
        }
        return false;
    }
}
