import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ShellController {

    private String[] validCommands = {"exit", "echo", "type", "pwd"};

    public void echo(String command){
        String textBack = command.replace("echo ", "").trim();
        System.out.println(textBack);
    }

    public void exit(String command){
        command = command.replace("exit ", "").trim();
        int code = Integer.parseInt(command);
        System.exit(code);
    }

    public void type(String input){
        String extractedCommand = input.replace("type ", "").trim();
        boolean isValidCommand = isValidCommand(extractedCommand);

        String localPath = extractPath(extractedCommand);
        boolean hasValidPath = localPath != null;


        if(isValidCommand)
            System.out.println(extractedCommand + " is a shell builtin");
        else if (hasValidPath)
            System.out.println(extractedCommand + " is " + localPath);
        else
            System.out.println(extractedCommand + ": not found");

    }

    private boolean isValidCommand(String extractedCommand) {
        for(String command : this.validCommands){
            if (extractedCommand.equals(command)) {
                return true;
            }
        }
        return false;
    }

    //TODO: refatorar para ser win ou linux
    // get directories from 'Path' env variables
    public String extractPath(String input){
        String command = input.split(" ")[0].trim();
        //splitting by ':', unix-like systems
        String[] paths = System.getenv("PATH").split(":");
        for(String path : paths){
            Path fullPath = Path.of(path, command);
            if(Files.isRegularFile(fullPath)){
                return fullPath.toString();
            }
        }
        return null;
    }

    // when given a file that can be executed, execute with the parameter passed
    public void execute(String input){
        // get command to be found on env variables
        String path = extractPath(input);

        if(path != null){
            String[] commandWithArgs = input.split(" ");
            try{
                // building a process with the valid args and showing on default output
                ProcessBuilder builder = new ProcessBuilder(commandWithArgs);
                Process process = builder.start();
                process.getInputStream().transferTo(System.out);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }else{
            String command = input.split(" ")[0];
            System.out.println(command + ": command not found");
        }
    }

    // a command to show the current directory for the user
    public void pwd(){
        String workingDirectory = System.getProperty("user.dir");
        Path currentPath = Path.of(workingDirectory);
        System.out.println(currentPath.toAbsolutePath());
    }

}
