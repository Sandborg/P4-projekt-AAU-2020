package lab7;
import java.io.PrintStream;
import java.io.FileWriter;
import java.io.IOException;

import java_cup.parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/** Obsolete do not use */
public class PrintTree implements Visitable {
    PrintStream ps;
    public PrintTree(PrintStream ps) {
       this.ps = ps;
   }
    int prevLevel;
    public void pre(int level,  AbstractNode n) {
       String tab = "";
       for (int i=1; i <= level; ++i) tab += "  ";
       ps.println(tab + n.toString());
    }
    public void post(int i, AbstractNode a) {}
}
