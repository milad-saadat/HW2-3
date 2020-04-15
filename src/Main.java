import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws CloneNotSupportedException {
        OS.initialize();
        Drive.initialize();
        CommandProcess.commandProcessor();
    }
}
