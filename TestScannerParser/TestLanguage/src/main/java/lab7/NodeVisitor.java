package lab7;
import java.lang.reflect.*;
import java.io.PrintStream;

/** Visitor idea adapted from http://www.javaworld.com/javaworld/javatips/jw-javatip98.html
  */

public abstract class NodeVisitor extends ReflectiveVisitor {

   protected int level;
   protected PrintStream ps;

   public NodeVisitor() { level = 0; ps = System.err; }

   /** Change the output stream.  Default is System.out */
   public void setPrintStream(PrintStream ps) { this.ps = ps; }

   /** Default visitation if nothing suitable is found */
   public void defaultVisit(Object o) {
      throw new Error("The visitor " + this.getClass() + 
      " lacks the method visit(" + o.getClass() + ")");
   }

   /** Useful to be able to visit all children. The level instance variable is available to
      enhance your output (indent if you like) */
   protected final void visitChildren(AbstractNode n) {
      if (n == null) return;
      level = level + 1;
      for (AbstractNode c = n.getChild(); c != null; c = c.getSib())
         // dispatch(c);
         c.accept(this);
      level = level - 1;
   }

   public void err(String s) {
     err(this.ps, s);
   }
   public void err(PrintStream ps, String s) {
      out(ps, "Error " + s);
      throw new Error(s);
   }
   public void out(String s) {
      out(this.ps, s);
   }
   public void out(PrintStream ps, String s)  {
         String tab = "";
         for (int i=1; i <= level; ++i) tab += "  ";
         ps.println(tab + s);
   }




}
