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
        for(String command : this.validCommands){
            if (extractedCommand.equals(command)) {
                return true;
            }
        }
        return false;
    }

    // get directories from 'Path' env variables
    public String extractPath(String parameter){
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

    public void execute(String input){
        // get command to be found on env variables
        String command = input.split(" ")[0];
        String path = extractPath(command);

        if(path != null){
            String[] commandWithArgs = input.split(" ");
            try{
                ProcessBuilder builder = new ProcessBuilder(commandWithArgs);
                Process process = builder.start();
                process.getInputStream().transferTo(System.out);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }else{
            System.out.println(command + ": command not found");
        }
    }

}
