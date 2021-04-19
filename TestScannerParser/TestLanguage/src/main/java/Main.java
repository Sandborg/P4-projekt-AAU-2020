import java.io.File;
import java.io.FileReader;

import AST.ProgramNode;
import AST.SingleNode;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.XMLElement;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import lab7.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class Main {
    public JSONArray allNodes = new JSONArray();
    public static void main(String[] args) throws Exception{
        //Syntax Analysis
        parser p = new parser(new Scanner(new FileReader(args[0])));
        p.parse();

        // Contextual Analysis
        SymbolTable global = new SymbolTable(null);
        Analyzer analyzer = new Analyzer((ProgramNode)p.action_obj.prog,global);
    }

}
