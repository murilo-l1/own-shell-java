import java.util.Scanner;

public class Main {

   /* static String[] validCommands = {"cd", "echo"};
    static String invalidCommandMessage = ": command not found";*/

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("$ ");
            String input = scanner.nextLine();

            if(input != null){
                System.out.println(input + ": command not found");
            }
        }
    }

}
