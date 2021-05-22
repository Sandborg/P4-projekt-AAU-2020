package AST;

import AST.Visitor.Visitor;

import org.json.simple.JSONObject;
/** All AST nodes are subclasses of this node.  This node knows how to
  * link itself with other siblings and adopt children.
  * Each node gets a node number to help identify it distinctly in an AST.
  */
public abstract class AbstractNode {

   private AbstractNode mysib;
   private AbstractNode parent;
   private AbstractNode child;
   private AbstractNode firstSib;
   public JSONObject node = new JSONObject();

   public AbstractNode() {
      parent   = null;
      mysib    = null;
      firstSib = this;
      child    = null;
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


   public void setParent(AbstractNode p) {
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

   public String getName() { return ""; }
   public String getValueString() {return "";}
   public String getType() { return ""; }
   public String getIdType() {return "";}

   public String toString() {
      return("" + getName());
   }

   public abstract void accept(Visitor analyzer);
   public abstract void accept(Visitor analyzer, AbstractNode parent);
}
