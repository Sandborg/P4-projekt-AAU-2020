import java.util.*;
import lab7.*;
public class SymbolTable {
    public String name;

    public Map<String, AbstractNode> table;
    public Map<String, SymbolTable> scopes;

    //Refrence to scope above this scope :D
    public SymbolTable prev;

    public SymbolTable (SymbolTable prev) {
        table = new HashMap<String, AbstractNode>();
        scopes = new HashMap<String, SymbolTable>();

        this.prev = prev;
    }

    //put node / symbol into table
    public void put(String s, AbstractNode n) {
        if(table.get(s) != null) {
            System.out.println(s + " is already declared :D");
        }else{
            table.put(s,n);
        }
    }

    //put scope into table
    public void put(String s, SymbolTable table) {
        if(scopes.get(s) != null) {
            System.out.println(s + " is already declared :D");
        }else {
            scopes.put(s, table);
        }
    }

    public AbstractNode get(String s) {
        for(SymbolTable t = this; t != null; t = t.prev) {
            AbstractNode found = t.table.get(s);
            if(found != null) return found;
        }
        return null;
    }
}