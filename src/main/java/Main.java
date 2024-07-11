import java.util.Scanner;

public class Main {

    static String[] validCommands = {"exit 0"};


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


}