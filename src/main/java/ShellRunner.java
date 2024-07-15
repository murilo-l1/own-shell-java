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
            else if(input.equals("pwd")){
                controller.pwd();
            }
            else if(input.startsWith("cd")){
                controller.cd(input);
            }
            else{
                if(controller.extractPath(input) != null){
                   controller.execute(input);
                }else{
                    System.out.println(input + ": command not found");
                }
            }
        }
    }

}