import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class Main {

    static String[] validCommands = {"exit", "echo", "type"};

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("$ ");
            String input = scanner.nextLine();

            if(input.startsWith("exit")){
                exit(input);
            }
            else if(input.startsWith("echo")){
                echo(input);
            }
            else if(input.startsWith("type")){
                type(input);
            }
            else{
                System.out.println(input + ": command not found");
            }
        }
    }

    private static void echo(String command){
        String textBack = command.replace("echo ", "").trim();
        System.out.println(textBack);
    }

    private static void exit(String command){
        command = command.replace("exit ", "").trim();
        int code = Integer.parseInt(command);
        System.exit(code);
    }

    private static void type(String input){
        String extractedCommand = input.replace("type ", "").trim();
        String localPath = extractPath(extractedCommand);
        boolean isValidCommand = false;
        boolean hasValidPath = false;

        for(String command : validCommands){
            if (extractedCommand.equals(command)) {
                isValidCommand = true;
                break;
            }
        }

        if(localPath != null)
            hasValidPath = true;


        if(isValidCommand)
            System.out.println(extractedCommand + " is a shell builtin");
        else if (hasValidPath)
            System.out.println(extractedCommand + " is " + localPath);
        else
            System.out.println(extractedCommand + ": not found");

    }

    // get directories from 'Path' env variables
    private static String extractPath(String parameter){
        String[] paths = System.getenv("PATH").split(":");
        for(String path : paths){
            Path fullPath = Path.of(path, parameter);
            if(Files.isRegularFile(fullPath)){
                return fullPath.toString();
            }
        }
        return null;
    }



}