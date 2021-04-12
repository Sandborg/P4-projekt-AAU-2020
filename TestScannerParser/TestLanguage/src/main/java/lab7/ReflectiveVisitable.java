package lab7;

/** Reflective visitor pattern -- a node must accept a visitor */
public interface ReflectiveVisitable {
   public void accept(ReflectiveVisitor rv);
}
