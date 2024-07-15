import java.util.Scanner;


public class ShellRunner {

    public static void main(String[] args) {
        ShellController controller = new ShellController();
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.print("$ ");
            String input = scanner.nextLine();

            if(input.startsWith("exit")){
                controller.exit(input);
            }
            else if(input.startsWith("echo")){
                controller.echo(input);
            }
            else if(input.startsWith("type")){
                controller.type(input);
            }
            else{
                if(controller.findExecutablePath(input) != null){
                   controller.execute(input);
                }else{
                    System.out.println(input + ": command not found");
                }
            }
        }
    }

}