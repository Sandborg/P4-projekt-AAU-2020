import java.io.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

public class CompileCommand {

    public CompileCommand() throws InterruptedException, IOException {
        //Paths for the C and .exe file in CCompiler and their the destination in resources.
        Path C_file = Path.of("src/main/CCompiler/tcc/program.c");
        Path Executable = Path.of("src/main/CCompiler/tcc/program.exe");
        Path C_file_Destination = Path.of("src/main/resources/program.c");
        Path Exe_Destination = Path.of("src/main/resources/program.exe");

        //Compile the C file in tcc with the compile_code.bat. An exit command is commented out in the bat file, it will stop the terminal from showing up
        Runtime.getRuntime().exec("cmd /c start compile_code.bat",null,new File("src/main/CCompiler/tcc/"));

        //Needs 1 second to compile before the .exe file can be moved.
        Thread.sleep(1000);

        //Move both files to the resources folder.
        Files.move(C_file, C_file_Destination, REPLACE_EXISTING);
        Files.move(Executable, Exe_Destination,REPLACE_EXISTING);
    }
}
