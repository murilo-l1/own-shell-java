import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ShellController {

    private String currentDirectory = System.getProperty("user.dir");
    private String[] validCommands = {"exit", "echo", "type", "pwd", "cd"};

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

        String separator = getCorrectSeparator();
        String[] paths = System.getenv("PATH").split(separator);

        for(String path : paths){
            Path fullPath = Path.of(path, command);
            if(Files.isRegularFile(fullPath)){
                return fullPath.toString();
            }
        }
        return null;
    }

    //splitting by ':' - unix-like systems, ';' - Windows systems
    private String getCorrectSeparator(){
        String systemOS = System.getProperty("os.name").toLowerCase();
        return systemOS.contains("win")
                ? ";"
                : ":";
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

    // print working directory
    public void pwd(){
        System.out.println(this.currentDirectory);
    }

    // change directory
    public void cd(String input){
        //considering an abs path being passed
        String pathReceived = input.split(" ")[1].trim();

        // crates a new path from the path received
        Path formattedPath = Path.of(pathReceived);

        // if is not a directory log the error
        if(!Files.isDirectory(formattedPath)){
            System.out.print("cd: " + pathReceived + ": No such file or directory\n");
        }else{
            // update the current directory of the controller
            currentDirectory = formattedPath.toString();
        }

    }

}
