
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Thu Apr 15 14:09:01 CEST 2021
//----------------------------------------------------

import java_cup.runtime.*;
import lab7.*;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Thu Apr 15 14:09:01 CEST 2021
  */
public class parser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\024\000\002\002\004\000\002\002\003\000\002\005" +
    "\003\000\002\005\004\000\002\005\003\000\002\006\005" +
    "\000\002\006\006\000\002\003\005\000\002\003\004\000" +
    "\002\004\005\000\002\004\005\000\002\004\005\000\002" +
    "\004\005\000\002\004\005\000\002\004\003\000\002\004" +
    "\003\000\002\004\004\000\002\010\003\000\002\007\003" +
    "\000\002\007\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\041\000\014\006\005\013\010\014\006\016\011\017" +
    "\013\001\002\000\004\002\043\001\002\000\010\006\005" +
    "\013\010\014\006\001\002\000\016\004\ufff2\005\ufff2\006" +
    "\ufff2\007\ufff2\010\ufff2\011\ufff2\001\002\000\010\002\000" +
    "\016\011\017\013\001\002\000\016\004\ufff3\005\ufff3\006" +
    "\ufff3\007\ufff3\010\ufff3\011\ufff3\001\002\000\004\015\uffef" +
    "\001\002\000\016\002\ufffd\006\005\013\010\014\006\016" +
    "\ufffd\017\ufffd\001\002\000\004\015\uffee\001\002\000\004" +
    "\015\032\001\002\000\010\002\uffff\016\uffff\017\uffff\001" +
    "\002\000\016\004\021\005\020\006\022\007\024\010\023" +
    "\011\017\001\002\000\010\006\005\013\010\014\006\001" +
    "\002\000\010\006\005\013\010\014\006\001\002\000\016" +
    "\002\ufff9\006\ufff9\013\ufff9\014\ufff9\016\ufff9\017\ufff9\001" +
    "\002\000\010\006\005\013\010\014\006\001\002\000\010" +
    "\006\005\013\010\014\006\001\002\000\010\006\005\013" +
    "\010\014\006\001\002\000\016\004\ufff6\005\ufff6\006\ufff6" +
    "\007\ufff6\010\ufff6\011\ufff6\001\002\000\016\004\ufff5\005" +
    "\ufff5\006\ufff5\007\ufff5\010\ufff5\011\ufff5\001\002\000\016" +
    "\004\ufff7\005\ufff7\006\ufff7\007\024\010\023\011\017\001" +
    "\002\000\016\004\ufff8\005\ufff8\006\ufff8\007\024\010\023" +
    "\011\017\001\002\000\016\004\ufff4\005\ufff4\006\ufff4\007" +
    "\ufff4\010\ufff4\011\ufff4\001\002\000\006\004\ufff0\012\ufff0" +
    "\001\002\000\006\004\035\012\034\001\002\000\010\006" +
    "\005\013\010\014\006\001\002\000\010\002\ufffc\016\ufffc" +
    "\017\ufffc\001\002\000\016\002\ufffb\006\005\013\010\014" +
    "\006\016\ufffb\017\ufffb\001\002\000\016\004\040\005\020" +
    "\006\022\007\024\010\023\011\017\001\002\000\016\002" +
    "\ufffa\006\ufffa\013\ufffa\014\ufffa\016\ufffa\017\ufffa\001\002" +
    "\000\010\002\ufffe\016\ufffe\017\ufffe\001\002\000\016\004" +
    "\ufff1\005\ufff1\006\ufff1\007\024\010\023\011\017\001\002" +
    "\000\004\002\001\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\041\000\016\002\003\003\011\004\015\005\006\006" +
    "\014\007\013\001\001\000\002\001\001\000\004\004\041" +
    "\001\001\000\002\001\001\000\006\006\040\007\013\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\004\036" +
    "\001\001\000\002\001\001\000\004\010\032\001\001\000" +
    "\002\001\001\000\002\001\001\000\004\004\030\001\001" +
    "\000\004\004\027\001\001\000\002\001\001\000\004\004" +
    "\026\001\001\000\004\004\025\001\001\000\004\004\024" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\006\003\035\004\015\001\001\000" +
    "\002\001\001\000\004\004\036\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {



   public JSONArray allNodes = new JSONArray();
   public JSONArray allNodesUnderProg = new JSONArray();
   public JSONArray expressions = new JSONArray();

      public AbstractNode addAllNodesToJSON (AbstractNode n, boolean addToList) {
                     if(n.getChild() != null) {
                         n.node.put("Body", AddAllChild(n.getChild(),new JSONArray()));
                         if(addToList) {
                             allNodes.add(n.node);
                         }
                     }
                     return null;
                 }

                 public JSONArray AddAllChild (AbstractNode n, JSONArray array) {
                     array.add(n.node);
                     if(n.getChild() != null)
                     {
                         addAllNodesToJSON(n,false);
                     }

                     if(n.getSib() != null) {
                         AddAllChild(n.getSib(),array);
                     }
                     return array;
                 }
   class BinaryOPNode extends AbstractNode {
         AbstractNode number1;
         AbstractNode number2;
         String operator;

         public BinaryOPNode (AbstractNode number1, AbstractNode number2, String operator) {
            this.number1 = number1;
            this.number2 = number2;
            this.operator = operator;


            node.put("left", number1.node);
            node.put("right", number2.node);
            node.put("type", "BinaryExpression");
            node.put("operator", operator);

         }
         public String getName() {return number1 + " " + operator + " " + number2;}
   }

   class VariableDeclarationNode extends AbstractNode {
        AbstractNode type;
        AbstractNode id;
        AbstractNode body;

        public VariableDeclarationNode (AbstractNode type, AbstractNode id, AbstractNode body) {
            this.type = type;
            this.id = id;
            this.body = body;

            node.put("type", "VariableDeclaration");
            node.put("Identifier", id.node);
            node.put("VariableType", type.node);
            if(body != null) {
                node.put("Init", body.node);
            }
        }
   }

   class IdentifierNode extends AbstractNode {
        String id;

        public IdentifierNode (String id) {
            this.id = id;
            node.put("type", "Identifier");
            node.put("id", id);
        }
        public String getValue() {return id;}
   }

   class TypeNode extends AbstractNode {
        String type;

        public TypeNode (String type) {
            this.type = type;
            node.put("type", "Type");
            node.put("dataType", type);
        }

        public String getValue() {return type;}
   }

   class IntegerNode extends AbstractNode {
        int value;
        public IntegerNode(int value) {
            this.value = value;
            node.put("type", "Literal");
            node.put("value", value);

        }

        public int getValue() {return value;}
   }

      class FloatNode extends AbstractNode {
           float value;
           public FloatNode(float value) {
               this.value = value;
               node.put("type", "Decimal");
               node.put("value", value);

           }

           public float getValue() {return value;}
      }

   class SingleNode extends AbstractNode {
         String name;


         public SingleNode(String name) {
            this.name = name;
         }
         public String getName() {return name;}
   }

   public AbstractNode makeNode(AbstractNode number1, AbstractNode number2, String operator) {return new BinaryOPNode(number1,number2, operator);}
   public AbstractNode makeNode(int value) {return new IntegerNode(value);}
   public AbstractNode makeNode(float value) {return new FloatNode(value);}
   public AbstractNode makeNode(String name) {return new SingleNode(name);}
   public AbstractNode makeNode(AbstractNode type, AbstractNode id, AbstractNode body) {return new VariableDeclarationNode(type,id, body);}

   public AbstractNode makeTypeNode(String type) {return new TypeNode(type);}
   public AbstractNode makeIdentifierNode(String id) {return new IdentifierNode(id);}

  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // TypeName ::= DECIMAL_TYPE 
            {
              AbstractNode RESULT =null;
		int typeleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int typeright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Object type = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeTypeNode("decimal"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("TypeName",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // TypeName ::= INT_TYPE 
            {
              AbstractNode RESULT =null;
		int typeleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int typeright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Object type = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeTypeNode("int"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("TypeName",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // Identifier ::= IDENTIFIER 
            {
              AbstractNode RESULT =null;
		int idleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int idright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		String id = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeIdentifierNode(id); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("Identifier",6, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // expr ::= MINUS expr 
            {
              AbstractNode RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode e = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // expr ::= DECIMAL 
            {
              AbstractNode RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Float n = (Float)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(n); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // expr ::= INTEGER 
            {
              AbstractNode RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer n = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(n); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // expr ::= expr MOD expr 
            {
              AbstractNode RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode e1 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode e2 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(e1, e2, "%"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // expr ::= expr DIVIDE expr 
            {
              AbstractNode RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode e1 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode e2 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(e1, e2, "/"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // expr ::= expr TIMES expr 
            {
              AbstractNode RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode e1 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode e2 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(e1, e2, "*"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // expr ::= expr MINUS expr 
            {
              AbstractNode RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode e1 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode e2 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(e1, e2, "-"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // expr ::= expr PLUS expr 
            {
              AbstractNode RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode e1 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode e2 = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(e1, e2, "+"); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // expr_list ::= expr SEMI 
            {
              AbstractNode RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		AbstractNode e = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = e; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr_list",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // expr_list ::= expr_list expr SEMI 
            {
              AbstractNode RESULT =null;
		int elleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int elright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode el = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		AbstractNode e = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = el.makeSibling(e); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr_list",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // TypeDeclaration ::= TypeName Identifier EQUALS expr_list 
            {
              AbstractNode RESULT =null;
		int tnleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).left;
		int tnright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).right;
		AbstractNode tn = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-3)).value;
		int idleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int idright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode id = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int elleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int elright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode el = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = makeNode(tn,id,el); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("TypeDeclaration",4, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // TypeDeclaration ::= TypeName Identifier SEMI 
            {
              AbstractNode RESULT =null;
		int tnleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int tnright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		AbstractNode tn = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int idleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int idright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		AbstractNode id = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = makeNode(tn,id,makeNode("lol")); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("TypeDeclaration",4, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // TypeDeclarations ::= expr_list 
            {
              AbstractNode RESULT =null;
		int elleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int elright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode el = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		  RESULT = el; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("TypeDeclarations",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // TypeDeclarations ::= TypeDeclarations TypeDeclaration 
            {
              AbstractNode RESULT =null;
		int tdsleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int tdsright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		AbstractNode tds = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int tdleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int tdright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode td = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		  RESULT = tds.makeSibling(td); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("TypeDeclarations",3, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // TypeDeclarations ::= TypeDeclaration 
            {
              AbstractNode RESULT =null;
		int tdleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int tdright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode td = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		  RESULT = td; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("TypeDeclarations",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // CompilationUnit ::= TypeDeclarations 
            {
              AbstractNode RESULT =null;
		int tdleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int tdright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		AbstractNode td = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		

    AbstractNode prog = makeNode("prog");
    System.out.println(td);
    prog.node.put("type", "Program");

    prog.adoptChildren(td);
    addAllNodesToJSON(prog,true);


     try (FileWriter file = new FileWriter("ast.json")) {
                //We can write any JSONArray or JSONObject instance to the file
                file.write(allNodes.toJSONString());
                file.flush();

     } catch (IOException e) {
         e.printStackTrace();
     }

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CompilationUnit",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= CompilationUnit EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		AbstractNode start_val = (AbstractNode)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

