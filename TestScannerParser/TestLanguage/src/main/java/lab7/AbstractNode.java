package lab7;

import java_cup.runtime.*;
import java.util.Vector;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/** All AST nodes are subclasses of this node.  This node knows how to
  * link itself with other siblings and adopt children.
  * Each node gets a node number to help identify it distinctly in an AST.
  */
public abstract class AbstractNode implements ReflectiveVisitable, NodeDumpable {
   private static int nodeNums = 0;
   private int nodeNum;
   private AbstractNode mysib;
   private AbstractNode parent;
   private AbstractNode child;
   private AbstractNode firstSib;
   private Type         type;

   public AbstractNode() {
      parent   = null;
      mysib    = null;
      firstSib = this;
      child    = null;
      nodeNum = ++nodeNums;
   }

   /** Join the end of this sibling's list with the supplied sibling's list */
   public AbstractNode makeSibling(AbstractNode sib) {
      if (sib == null) throw new Error("Call to makeSibling supplied null-valued parameter");
      AbstractNode appendAt = this;
      while (appendAt.mysib != null) appendAt = appendAt.mysib;
      appendAt.mysib = sib.firstSib;


      AbstractNode ans = sib.firstSib;
      ans.firstSib = appendAt.firstSib;
      while (ans.mysib != null) {
         ans = ans.mysib;
         ans.firstSib = appendAt.firstSib;
      }
      return(ans);
   }

   /** Adopt the supplied node and all of its siblings under this node */
   public AbstractNode adoptChildren(AbstractNode n) {
      if (n != null) {
         if (this.child == null) this.child = n.firstSib;
         else this.child.makeSibling(n);
      }
      for (AbstractNode c = this.child; c != null; c = c.mysib) c.parent = this;
      return this;
   }

   public AbstractNode orphan() {
      mysib = parent = null;
      firstSib = this;
      return this;
   }

   public AbstractNode abandonChildren() {
      child = null;
      return this;
   }

   private void setParent(AbstractNode p) {
      this.parent = p;
   }

   public AbstractNode getParent() {
      return(parent);
   }

   public AbstractNode getSib() {
      return(mysib);
   }

   public AbstractNode getChild() {
      return(child);
   }

   public AbstractNode getFirst() {
      return(firstSib);
   }

   public Type getNodeType() { return type; }
   public void setNodeType(Type type) { this.type = type; }

   public String getName() { return ""; }
   
   public String toString() {
      return("" + getName());
   }

   public String dump() {
      Type t = getNodeType();
      String tString = (t != null) ? ("<"+t.toString()+"> ") : "";

     return  "" + getNodeNum() + ": " + tString + whatAmI() +
            "  \"" + toString()+"\"";
   }


   public int getNodeNum() { return nodeNum; }

   private static String trimClass(String cl) {
      int dollar = cl.lastIndexOf('$');
      int dot    = cl.lastIndexOf('.');
      int trimAt = (dollar > dot) ? dollar : dot;
      if (trimAt >= 0) cl = cl.substring(trimAt+1);
      return cl;
   }

   private static Class objectClass = (new Object()).getClass();
   private static void debugMsg(String s) { }
   private static Enumeration interfaces(Class c) {
      Class iClass = c;
      Vector v = new Vector();
      while (iClass != objectClass) {
	debugMsg("Looking for interface  match in " + iClass.getName());
	Class[] interfaces = iClass.getInterfaces();
         for (int i = 0; i < interfaces.length; i++) {
	      debugMsg("   trying interface " + interfaces[i]);
              v.addElement(interfaces[i]);
            Class[] superInterfaces = interfaces[i].getInterfaces();
            for (int j=0; j < superInterfaces.length; ++j) {
	      debugMsg("   trying super interface " + superInterfaces[j]);
                  v.addElement(superInterfaces[j]);
            }

         }
	 iClass = iClass.getSuperclass();
      }
      return v.elements();
   }

   /** Reflectively indicate the class of "this" node */
   public String whatAmI() {
      Set s = new HashSet();
      String ans = trimClass(getClass().toString());
      Enumeration e = interfaces(getClass());
      while (e.hasMoreElements()) {
         Class c = (Class) e.nextElement();
         String str = trimClass(c.toString());
         if (!(str.equals("DontPrintMe") || str.equals("ReflectiveVisitable")))
            s.add(trimClass(c.toString()));
      }
      return ans + s.toString();
   }

   private void internWalk(int level, Visitable v) {
      v.pre(level, this);
      for (AbstractNode c = child; c != null; c=c.mysib)
         c.internWalk(level+1, v);
      v.post(level, this);
   }

   /** Reflective visitor pattern */
   public final void accept(ReflectiveVisitor v) { v.dispatch(this); }

   /** Obsolete, do not use! */
   public void walkTree(Visitable v) {
      internWalk(0, v);
   }
}
