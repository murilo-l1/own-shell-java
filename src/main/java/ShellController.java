import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShellController {

    private String[] validCommands = {"exit", "echo", "type"};

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
        boolean isValidCommand = false;
        for(String command : this.validCommands){
            if (extractedCommand.equals(command)) {
                isValidCommand = true;
                break;
            }
        }
        return isValidCommand;
    }

    // get directories from 'Path' env variables
    private String extractPath(String parameter){
        //splitting by ':', unix-like systems
        String[] paths = System.getenv("PATH").split(":");
        for(String path : paths){
            Path fullPath = Path.of(path, parameter);
            if(Files.isRegularFile(fullPath)){
                return fullPath.toString();
            }
        }
        return null;
    }

    //iterate over env variables and check if is exec
    public String findExecutablePath(String input){
        String command = input.split(" ")[0];
        String[] paths = System.getenv("PATH").split(":");

        for(String path : paths){
            Path fullPath = Path.of(path, command);
            if(Files.isExecutable(fullPath)){
                return fullPath.toString();
            }
        }
        return null;
    }

    public void execute(String input) {
        String fullPath = findExecutablePath(input);
        try{
            Process p = Runtime.getRuntime().exec(fullPath.split(" "));
            p.getInputStream().transferTo(System.out);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

}
