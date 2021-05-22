import java.io.FileReader;
import AST.ProgramNode;

public class Main {
    public static void main(String[] args) throws Exception{
        //Syntax Analysis
        parser p = new parser(new Scanner(new FileReader(args[0])));
        p.parse();

        //Write AST
        p.action_obj.WriteAST("ast.json");

        // Contextual Analysis
        SymbolTable global = new SymbolTable(null);
        Analyzer analyzer = new Analyzer((ProgramNode)p.action_obj.prog,global);

        //Update AST
        p.action_obj.WriteAST("ast.json");

        //Code Gen
        CodeGenerator codeGenerator = new CodeGenerator();
        CompileCommand compileCommand = new CompileCommand();
    }
}
