import java.util.Scanner;

public class Main {

   /* static String[] validCommands = {"cd", "echo"};
    static String invalidCommandMessage = ": command not found";*/

    public static void main(String[] args) throws Exception {

        System.out.print("$ ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        /*for(String command : validCommands){
            if(input.equals(command)){
                System.out.println("Command found!");
            }
        }*/
        System.out.printf(input + ": command not found");

    }
}
