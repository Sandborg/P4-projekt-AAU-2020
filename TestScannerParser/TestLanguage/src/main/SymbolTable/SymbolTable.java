import java.util.*;
import lab7.*;
public class SymbolTable {
    public String name;

    public Map<String, AbstractNode> table;
    public Map<String, SymbolTable> scopes;

    //Refrence to scope above this scope
    public SymbolTable prev;

    public SymbolTable (SymbolTable prev) {
        table = new HashMap<String, AbstractNode>();
        scopes = new HashMap<String, SymbolTable>();

        this.prev = prev;
    }

    //put node / symbol into table
    public Boolean put(String s, AbstractNode n) {
        if(table.get(s) != null) {
            return false;
        }else{
            table.put(s,n);
        }
        return true;
    }

    //put scope into table
    public Boolean put(String s, SymbolTable table) {
        if(scopes.get(s) != null) {
            return false;
        }else {
            scopes.put(s, table);
        }
        return true;
    }

    public AbstractNode get(String s) {
        for(SymbolTable t = this; t != null; t = t.prev) {
            AbstractNode found = t.table.get(s);
            if(found != null) return found;
        }
        return null;
    }
}