import java.util.Scanner;

public class Main {

    static String[] validCommands = {"exit 0"};


    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("$ ");
            String input = scanner.nextLine();

            if(input.equals("exit 0")){
                System.exit(0);
            }else{
                System.out.println(input + ": command not found");
            }
        }
    }

}