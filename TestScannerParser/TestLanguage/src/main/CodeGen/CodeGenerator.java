import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CodeGenerator {

    public CodeGenerator() throws IOException, ParseException {
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

        //Get the body from the global AST
        for (Object o : obj) {
            JSONObject object = (JSONObject) o;
            body = (JSONArray) object.get("Body");
        }

        //Always include these C libraries
        program.write("#include <stdio.h>\n");
        program.write("#include <string.h>\n");
        program.write("#include <stdlib.h>\n");

        //Then add prototypes and definitions and imports
        for (Object o : body) {
            JSONObject thisObject = (JSONObject) o;
            if (thisObject.get("type") != null) {
                if (thisObject.get("type").equals("FunctionDeclaration")) {
                    program.append(GetFunctionDec(thisObject, "") + ";\n");
                }else if (thisObject.get("type").equals("FunctionDefinition")) {
                    program.append(GetFunctionDef(thisObject, "") + "\n");
                }else if(thisObject.get("type").equals("import")) {
                    JSONObject name = (JSONObject)thisObject.get("name");
                    program.append(GetLibrary((String)name.get("value"), ""));
                }
            }
        }

        //Add main
        program.append("int main() { \n");
        program.append(GetBody(body, "", false, null));
        program.append("\nreturn 0;\n}");
    }

    public String GetBody(JSONArray body, String expr, Boolean insideFunction, JSONArray params) {
        //Append and last value are used for strings.
        expr += "char append[2000];\n";
        expr += "char lastValue[2000];\n";

        for (Object o : body) {
            JSONObject thisObject = (JSONObject) o;
            if (thisObject.get("type") != null) {
                switch((String)thisObject.get("type")) {
                    case "BinarayExpression" -> expr += GetBinaryOperator(thisObject, "",insideFunction) + ";\n";
                    case "int", "decimal" -> expr += (thisObject.get("value") + ";\n");
                    case "VariableDeclaration" ->  expr += (GetVariableDeclaration(thisObject, "", true, insideFunction) + ";\n");
                    case "AssignmentExpression" -> expr += (GetAssignmentExpression(thisObject, "", insideFunction, params) + ";\n");
                    case "FunctionCall" -> expr += (GetFunctionCall(thisObject, "", insideFunction) + "; \n");
                    case "ReturnStatement" -> expr += (GetReturnStatement(thisObject, "", insideFunction) + "; \n");
                    case "IfStatement" -> expr += (GetIfStatement(thisObject,"", insideFunction)+ "\n");
                    case "ForLoop" -> expr+=(GetForLoop(thisObject,"",insideFunction) + "\n");
                    default -> expr+="";
                }
            }
        }
        return expr;
    }

    public String GetReturnStatement(JSONObject o, String expr, Boolean insideFunction) {
        JSONObject argument = (JSONObject) o.get("Argument");
        expr += "return ";

        if (argument.get("type").equals("BinaryExpression")) {
            expr += GetBinaryOperator(argument, "",insideFunction);
        } else if (argument.get("type").equals("Identifier")) {
            expr += GetIdentifier(argument, "", insideFunction);
        } else {
            expr += argument.get("value");
        }
        return expr;
    }

    public String GetFunctionCall(JSONObject o, String expr, Boolean insideFunction) {
        JSONObject id = (JSONObject) o.get("id");
        JSONArray params = (JSONArray) o.get("params");

        if(id.get("id").equals("print")) {
            expr+=GetPrintfFunction((JSONObject)params.get(0),expr,insideFunction);
        }else {
            expr += id.get("id") + "(";
            if (params != null) {
                expr += GetParameters(params, "",insideFunction);
            }
            expr += ")";
        }
        return expr;
    }

    public String GetPrintfFunction (JSONObject o, String expr, Boolean insideFunction) {
        JSONObject left = (JSONObject)o.get("left");
        JSONObject right = (JSONObject)o.get("right");

        if(left != null) expr += GetPrintfFunction(left, "", insideFunction);
        if(right != null) expr += GetPrintfFunction(right, "", insideFunction);

        if(left == null) {
            if(o.get("type").equals("string")) expr+="printf(\"%s\"," + o.get("value") + ");\n";
            if(o.get("type").equals("FunctionCall")) {
                JSONObject id = (JSONObject)o.get("id");
                if(id.get("dataType").equals("string")) {
                    expr+="printf(\"%s\"," + GetFunctionCall(o,"", insideFunction) +");\n";
                }else if(id.get("dataType").equals("decimal")) {
                    expr+="printf(\"%f\"," + GetFunctionCall(o,"", insideFunction) +");\n";
                }else{
                    expr+="printf(\"%d\"," + GetFunctionCall(o,"", insideFunction) +");\n";
                }
            }
            if(o.get("type").equals("int")) expr+="printf(\"%d\"," + o.get("value") + ");\n";
            if(o.get("type").equals("decimal")) expr+="printf(\"%f\"," + o.get("value") + ");\n";
            if(o.get("type").equals("Identifier")) {
                if(o.get("dataType").equals("string")) {
                    expr+="printf(\"%s\"," + GetIdentifier(o,"",insideFunction) + ");\n";
                }else if(o.get("dataType").equals("decimal")) {
                    expr+="printf(\"%f\"," + GetIdentifier(o,"",insideFunction) + ");\n";
                }
                else{
                    expr+="printf(\"%d\"," + GetIdentifier(o,"",insideFunction) + ");\n";
                }
            }
        }
        return expr;
    }

    public String GetStringConcat (JSONObject o, String expr, JSONObject Identifier, Boolean insideFunction) {
        //NOTE: sprintf is used, to convert int to char

        JSONObject left = (JSONObject)o.get("left");
        JSONObject right = (JSONObject)o.get("right");
        if(left != null) expr += GetStringConcat(left, "", Identifier,insideFunction);
        if(right != null) expr += GetStringConcat(right, "", Identifier, insideFunction);

        if(left == null) {
            if(o.get("type").equals("string")) expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + "," + o.get("value") + ");\n";
            if(o.get("type").equals("FunctionCall")) {
                JSONObject id = (JSONObject)o.get("id");
                if(id.get("dataType").equals("string")) {
                    expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + "," + GetFunctionCall(o,"",insideFunction) + " );\n";
                }else {
                    expr += "sprintf(append ,\"%d\"," + GetFunctionCall(o, "", insideFunction) + ");\n";
                    expr += "strcat(" + GetIdentifier(Identifier, "", insideFunction) + ",append);\n";
                }
            }
            if(o.get("type").equals("int")) {
                expr+= "sprintf(append,\"%d\"," + o.get("value") + ");\n";
                expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + ",append);\n";
            }
            if(o.get("type").equals("decimal")) {
                expr+= "sprintf(append,\"%f\"," + o.get("value") + ");\n";
                expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + ",append);\n";
            }
            if(o.get("type").equals("Identifier")) {
                if(o.get("dataType").equals("string")) {
                    if(o.get("id").equals(Identifier.get("id"))) {
                        //If you write hej.val = hej.val + " med dig".
                        //Then we need to use the lastvalue variable, since it stores hej.val value.
                        expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + ",lastValue);\n";
                    }else{
                        expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + "," + GetIdentifier(o,"",insideFunction) + ");\n";
                    }
                }else if(o.get("dataType").equals("decimal")){
                    expr+= "sprintf(append,\"%f\"," + GetIdentifier(o,"",insideFunction) + ");\n";
                    expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + ",append);\n";
                }else{
                    expr+= "sprintf(append,\"%d\"," + GetIdentifier(o,"",insideFunction) + ");\n";
                    expr+="strcat(" + GetIdentifier(Identifier,"",insideFunction) + ",append);\n";
                }
            }
        }
        return expr;
    }

    public String GetFunctionDef(JSONObject o, String expr) {
        JSONObject type = (JSONObject) o.get("dataType");
        JSONObject id = (JSONObject) o.get("id");
        JSONArray params = (JSONArray) o.get("params");
        JSONArray body = (JSONArray) o.get("body");

        //Some cases are special. For example we want to translate decimal to float.
        if(type.get("dataType").equals("string")) {
            expr+="char * " + id.get("id") + " (";
        }else if(type.get("dataType").equals("decimal")) {
            expr+= "float " + id.get("id") + " (";
        }
        else {
            expr += type.get("dataType") + " " + id.get("id") + " (";
        }

        if (params != null) {
            expr += GetParameters(params, "",false) + "){\n";
        } else expr += "){\n";

        //This is for making the parameter pointers inside the function
        if(params != null) {
            expr += GetBodyParams(params, "");
        }

        if (body != null) {
            expr += GetBody(body, "", true, params) + "\n}\n";
        } else expr += "\n}\n";

        return expr;
    }

    public String GetBodyParams(JSONArray o, String expr) {
        for (Object obj : o) {
            JSONObject thisObject = (JSONObject) obj;
            JSONObject id = (JSONObject) thisObject.get("Identifier");
            JSONObject varType = (JSONObject) thisObject.get("VariableType");

            //We only need to make a pointer, if the parameter isn't adr.
            if (!id.get("idType").equals("adr")) {
                switch ((String) varType.get("dataType")) {
                    case "decimal" -> expr += "float *" + id.get("id") + "p = &" + id.get("id") + ";\n";
                    case "string" -> expr += "char **" + id.get("id") + "p = &" + id.get("id") + ";\n";
                    default -> expr += varType.get("dataType") + " *" + id.get("id") + "p = &" + id.get("id") + ";\n";
                }
            }
        }
        return expr;
    }

    public String GetFunctionDec(JSONObject o, String expr) {
        JSONObject funcType = (JSONObject) o.get("FuncType");
        JSONObject id = (JSONObject) o.get("Id");
        JSONArray params = (JSONArray) o.get("Params");
        if(funcType.get("dataType").equals("string")) {
            expr+="char * " + id.get("id");
        }else if(funcType.get("dataType").equals("decimal")) {
            expr+= "float " + id.get("id");
        }
        else {
            expr += funcType.get("dataType") + " " + id.get("id");
        }
        expr += "(";
        if (params != null) {
            expr += GetParameters((JSONArray) o.get("Params"), "",false);
        }
        expr += ")";

        return expr;
    }

    public String GetParameters(JSONArray o, String expr, Boolean insideFunction) {
        //i is used for checking if last parameter.
        int i = 0;
        for (Object obj : o) {
            JSONObject thisObject = (JSONObject) obj;
            if (thisObject.get("type").equals("VariableDeclaration")) {
                expr += GetVariableDeclarationParameter(thisObject, "");
            } else if (thisObject.get("type").equals("BinaryExpression")) {
                expr += GetBinaryOperator(thisObject, "", insideFunction);
            } else if (thisObject.get("type").equals("Identifier")) {
                if (thisObject.get("idType").equals("adr")) {
                    expr += "&" + thisObject.get("id") + "p";
                } else {
                    expr += "*" + thisObject.get("id") + "p";
                }
            } else {
                expr += thisObject.get("value");
            }
            //If it isnt the last parameter, set a comma
            if (i != o.size() - 1) {
                expr += ",";
            }
            i++;
        }
        return expr;
    }

    //This is needed since in parameters some pointers need 3 stars
    public String GetVariableDeclarationParameter(JSONObject o, String expr) {
        JSONObject id = (JSONObject) o.get("Identifier");
        JSONObject varType = (JSONObject) o.get("VariableType");

        if (id.get("idType").equals("adr")) {
            switch ((String) varType.get("dataType")) {
                case "decimal" -> expr += "float **" + id.get("id");
                case "string" -> expr += "char ***" + id.get("id");
                default -> expr += varType.get("dataType") + " **" + id.get("id");
            }

        } else {
            switch ((String) varType.get("dataType")) {
                case "decimal" -> expr += "float " + id.get("id");
                case "string" -> expr += "char *" + id.get("id");
                default -> expr += varType.get("dataType") + " " + id.get("id");
            }
        }
        return expr;
    }

    public String GetAssignmentExpression(JSONObject o, String expr, Boolean insideFunction, JSONArray params) {
        JSONObject left = (JSONObject) o.get("left");
        JSONObject right = (JSONObject) o.get("right");

        if (left.get("dataType").equals("string")) {
            expr += "strcpy(lastValue," + GetIdentifier(left,"",insideFunction) + ");";
            expr += "strcpy(" + GetIdentifier(left,"",insideFunction) + ", \"\");\n";
            expr += GetStringConcat(right,"",left,insideFunction);
        } else {
            //Make left side
            expr += GetIdentifier(left,"",insideFunction);

            expr += "=";

            //Make right side of assignment
            if (right.get("type").equals("BinaryExpression")) {
                expr += GetBinaryOperator(right, "",insideFunction);
            }else if(right.get("type").equals("FunctionCall")) {
                expr += GetFunctionCall(right,"",insideFunction);
            } else if (right.get("type").equals("Identifier")) {
                expr += GetIdentifier(right, "", insideFunction);
            } else {
                expr += right.get("value");
            }
        }
        return expr;
    }

    public String GetVariableDeclaration(JSONObject o, String expr, Boolean createPointer, Boolean insideFunction) {
        JSONObject init = (JSONObject) o.get("Init");
        JSONObject id = (JSONObject) o.get("Identifier");
        JSONObject varType = (JSONObject) o.get("VariableType");

        if (varType.get("dataType").equals("decimal")) {
            expr += "float " + id.get("id") + (init == null ? ";" : " ");
        } else if (varType.get("dataType").equals("string")) {
            expr += "char *" + id.get("id") + " = malloc(sizeof(char) * (2000)); \n";
            expr += "strcpy(" + id.get("id") + ", \"\" );\n";
        } else {
            expr += varType.get("dataType") + " " + id.get("id") + (init == null ? ";" : " ");
        }

        if (init != null && !varType.get("dataType").equals("string")) {
            if (init.get("type").equals("BinaryExpression")) {
                expr += " = " + GetBinaryOperator(init, "",insideFunction) + ";";
            }else if(init.get("type").equals("FunctionCall")) {
                expr += " = " + GetFunctionCall(init, "",insideFunction) + ";";
            }
            else if (init.get("type").equals("Identifier")) {
                expr += " = " + GetIdentifier(init,"",insideFunction) + ";";
            } else {
                if(!varType.get("dataType").equals("string")) {
                    expr+= " = " + init.get("value") +";";
                }
            }
        }

        if (createPointer) {
            if (varType.get("dataType").equals("decimal")) {
                expr += "float *" + id.get("id") + "p = " + "&" + id.get("id");
            } else if (varType.get("dataType").equals("string")) {
                expr += "char **" + id.get("id") + "p = " + "&" + id.get("id");
            } else {
                expr += "\n" + varType.get("dataType") + "*" + id.get("id") + "p = " + "&" + id.get("id");
            }
        }

        if(varType.get("dataType").equals("string") && init != null) {
            expr+= ";" + GetStringConcat(init,"",id,insideFunction);
        }else if(init != null && init.get("type").equals("BinaryExpression") && varType.get("dataType").equals("string")) {
            //If the variable is of type string, but it is asigned to binary expression.
            expr+= ";" + GetStringConcat(init,"",id,insideFunction);
        }
        return expr;
    }

    public String GetBinaryOperator(JSONObject o, String expr, Boolean insideFunction) {
        JSONObject left = (JSONObject) o.get("left");
        JSONObject right = (JSONObject) o.get("right");

        if (left.get("type").equals("BinaryExpression")) {
            expr += GetBinaryOperator(left, "",insideFunction);
        }else if(left.get("type").equals("FunctionCall")) {
            expr += GetFunctionCall(left, "",insideFunction);
        } else if (left.get("type").equals("Identifier")) {
            expr+= GetIdentifier(left,"",insideFunction);
        }else {
            expr += left.get("value");
        }

        expr += o.get("operator");

        if (right.get("type").equals("BinaryExpression")) {
            expr += GetBinaryOperator(right, "",insideFunction);
        }else if(right.get("type").equals("FunctionCall")) {
            expr += GetFunctionCall(right, "",insideFunction);
        } else if (right.get("type").equals("Identifier")) {
            expr+= GetIdentifier(right,"",insideFunction);
        } else {
            expr += right.get("value");
        }
        return expr;
    }

    public String GetIfStatement(JSONObject o, String s, Boolean insideFunction) {
        s+= "if(" + GetIfStatementTestCase(o, "",insideFunction) + ") { \n";
        s+= GetBody((JSONArray)o.get("ifBody"), "", insideFunction, null) + "\n}";

        if(o.get("elseBody") != null) {
            s+= "else { \n";
            s+= GetBody((JSONArray)o.get("elseBody"), "", insideFunction, null) + "\n}";
        }

        return s;
    }

    public String GetIfStatementTestCase(JSONObject o, String s, Boolean insideFunction) {
        JSONObject test = new JSONObject();

        //If test case isnt null
        if(o.get("test") != "{}") test = (JSONObject)o.get("test");
        if(test != null) {
            s+=GetLogicalExpression(test,"", insideFunction);
        }else{
            if(o.get("type").equals("Identifier")) {
                s+=GetIdentifier(o,"",insideFunction);
            }else if(o.get("type").equals("LogicalExpression")) {
                s+=GetLogicalExpression(o, "", insideFunction);
            }else{
                s+=o.get("value");
            }
        }
        return s;
    }

    public String GetLogicalExpression(JSONObject o, String s, Boolean insideFunction) {
        JSONObject left = (JSONObject)o.get("left");
        JSONObject right = (JSONObject)o.get("right");

        //Make left side of expression
        if(left != null && left.get("type").equals("BinaryExpression")) {
            s+=GetIfStatementTestCase((JSONObject)left.get("left"), "",insideFunction);
            s+=ConvertOperator((String)left.get("operator"));
            s+=GetIfStatementTestCase((JSONObject)left.get("right"), "",insideFunction);
        }else if(left != null && left.get("type").equals("Identifier")){
            s+=GetIdentifier(left,"",insideFunction);
        }else if(left != null && right.get("type").equals("LogicalExpression")) {
            s+=GetIfStatementTestCase(right,"",insideFunction);
        }else if(left != null){
            s+=left.get("value");
        }

        s+=ConvertOperator((String)o.get("operator"));

        //Make right side of expression
        if(right != null &&right.get("type").equals("BinaryExpression")) {
            s+=GetIfStatementTestCase((JSONObject)right.get("left"), "", insideFunction);
            s+=ConvertOperator((String)right.get("operator"));
            s+=GetIfStatementTestCase((JSONObject)right.get("right"), "", insideFunction);
        }else if(right != null && right.get("type").equals("Identifier")){
            s+=GetIdentifier(right,"",insideFunction);
        }else if(right != null && right.get("type").equals("LogicalExpression")) {
            s+=GetIfStatementTestCase(right,"", insideFunction);
        }else if(right != null){
            s+=right.get("value");
        }

        return s;
    }

    public String GetIdentifier(JSONObject o, String expr, Boolean insideFunction) {
        JSONObject isPram = (JSONObject)o.get("parameterInfo");
        if (insideFunction) {
            //Check if the left identifier is from the parameters
            if (isPram != null) {
                //Check if the parameter identifier is adr
                if (isPram.get("parameterType").equals("adr")) {
                    //Check if the current identifier is adr
                    if (o.get("idType").equals("adr")) {
                        expr += "*" + o.get("id");
                    } else {
                        expr += "**" + o.get("id");
                    }
                    //If the left parameter identifier is of type "val"
                } else {
                    if (o.get("idType").equals("adr")) {
                        expr += o.get("id") + "p";
                    } else {
                        expr += "*" + o.get("id") + "p";
                    }
                }
                //If the left parameter isn't from parameters:
            } else {
                if (o.get("idType").equals("adr")) {
                    expr += o.get("id") + "p ";
                } else {
                    expr += "*" + o.get("id") + "p";
                }
            }
            //If the assignment isn't inside a function
        } else {
            if (o.get("idType").equals("adr")) {
                expr += o.get("id") + "p";
            } else {
                expr += "*" + o.get("id") + "p";
            }
        }
        return expr;
    }

    public String GetForLoop(JSONObject o, String expr, Boolean insideFunction) {
        JSONObject testCase = (JSONObject)o.get("test");
        JSONObject initCase = (JSONObject)o.get("init");
        JSONObject continueCase = (JSONObject)o.get("continue");
        JSONArray body = (JSONArray)o.get("body");

        //Since everything is pointers in PIL, we need to make the variable dec before the loop.
        if(!initCase.get("type").equals("Identifier")) {
            expr+=GetVariableDeclaration(initCase,"",true, insideFunction) + "; \n";
        }

        expr += "for(";
        if(initCase.get("type").equals("Identifier")) {
            expr+=GetIdentifier(initCase,"",insideFunction);
        }else{
            //If it is a var dec, get its identifier.
            JSONObject id = (JSONObject)initCase.get("Identifier");
            expr+=GetIdentifier(id,"",insideFunction);
        }

        expr+=";" + GetLogicalExpression(testCase,"", insideFunction);
        expr+=";" + GetAssignmentExpression(continueCase,"",true,null);
        expr+=") { \n";
        expr+= GetBody(body,"",insideFunction, null);
        expr+="\n }";
        return expr;
    }

    public String GetLibrary (String name, String exp) throws IOException, ParseException {
        if(name.contains("\"")) {
            name = name.replace("\"", "");
        }
        JSONParser parser = new JSONParser();
        FileReader libAST = new FileReader("src/main/resources/" + name + ".json");
        JSONArray lib =(JSONArray)parser.parse(libAST);
        JSONArray libBody = new JSONArray();

        for(Object o : lib) {
            JSONObject object = (JSONObject) o;
            libBody = (JSONArray)object.get("Body");
        }

        for(Object o : libBody) {
            JSONObject thisObject = (JSONObject) o;
            if(thisObject.get("type") != null) {
                if(thisObject.get("type").equals("FunctionDeclaration")) {
                    exp += (GetFunctionDec(thisObject, "") + ";\n");
                }else if (thisObject.get("type").equals("FunctionDefinition")) {
                    exp += (GetFunctionDef(thisObject, "") + "\n");
                }
            }
        }
        return exp;
    }

    public String ConvertOperator(String operator) {
        return switch (operator) {
            case "IS" -> "==";
            case "IS_NOT" -> "!=";
            case "AND" -> "&&";
            case "OR" -> "||";
            case "GREATER" -> ">";
            case "GREATER_EQUALS" -> ">=";
            case "LESSER" -> "<";
            case "LESSER_EQUALS" -> "<=";
            case "%" -> "%";
            case "+" -> "+";
            case "-" -> "-";
            case "/" -> "/";
            case "*" -> "*";
            default -> "Unknown operator";
        };
    }
}
