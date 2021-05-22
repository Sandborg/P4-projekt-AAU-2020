package AST;

import AST.Visitor.Visitor;
import lab7.AbstractNode;
import org.json.simple.JSONObject;

public class AssignmentNode extends AbstractNode {
    public AbstractNode set;
    public AbstractNode to;

    public AssignmentNode(AbstractNode set, AbstractNode to) {
        this.set = set;
        this.to = to;
        node.put("type", "AssignmentExpression");
        node.put("left", set.node);
        node.put("right", to.node);
    }

    public String getName() {return set.getName();}
    public String getType() {return set.getType();}

    public void accept(Visitor v) {
        try {
            v.visitAssign(this);

        }catch(Exception e) {
            System.out.println(e + "\n");
            System.exit(0);
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
