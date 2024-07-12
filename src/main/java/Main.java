import java.util.Scanner;

public class Main {

    static String[] validCommands = {"exit", "echo", "type", "cat"};


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
        boolean isValid = false;

        for(String validCommand : validCommands){
            if(validCommand.equals(extractedCommand)){
                isValid = true;
            }
        }
        if(isValid){
            System.out.println(extractedCommand + " is a shell builtin");
        }else{
            System.out.println(extractedCommand + ": not found");
        }
    }

}