package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONObject;

public class AssignmentNode extends AbstractNode {
    public AbstractNode set;
    public AbstractNode to;
    public JSONObject varType;
    public AssignmentNode(AbstractNode set, AbstractNode to) {
        this.set = set;
        this.to = to;
        node.put("type", "AssignmentExpression");
        node.put("left", set.node);
        node.put("right", to.node);
    }

    public void UpdateRightNode (JSONObject s) {
        varType = s;
        node.put("right", s);
    }


    public String getName() {return set.getName();}
    public String getType() {return set.getType();}
    public void accept(Visitor v) {
        try {
            v.visitAssign(this);

        }catch(Exception e) {
            System.out.println(e + "\n");
        }
    }

    public void accept(Visitor v, AbstractNode parent) {
        try {
            v.visitAssign(this, parent);
        }catch(Exception e){
            System.out.println(e + "\n");
            System.exit(0);
        }
    }

}
