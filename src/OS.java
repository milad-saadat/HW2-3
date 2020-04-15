public class OS {
    private static String OSName;
    private static String OSVersion;

    public static void initialize(){

        while (true) {
            String line = Main.input.nextLine();
            line = line.trim();
            if (line.matches("install OS \\S+ \\S+")){
                String[] command = line.split(" ");
                OSName = command[2];
                OSVersion = command[3];
                break;
            }
            else {
                System.out.println("invalid command");
            }
        }
    }

    public static void printOSInfo(){
        System.out.println("OS is "+ OSName +" "+ OSVersion);
    }
}
