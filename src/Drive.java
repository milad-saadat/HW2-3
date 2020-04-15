import java.util.ArrayList;

public class Drive extends Directory {
    public static ArrayList<Drive> allDrives = new ArrayList<>();
    private int allSpace;

    public int getAllSpace() {
        return allSpace;
    }

    public Drive(int allSpace, String name) {
        this.allSpace = allSpace;
        this.name = name;
        this.parent = this;
        this.usedSpace = 0;
        this.root = this;
        allDrives.add(this);
    }

    public String getName() {
        return name;
    }

    public static void initialize(){
        int hardSize;
        int hardNum;
        int sizeUntilNow=0;

        while (true){
            String line = Main.input.nextLine();
            line = line.trim();
            if (line.matches("\\d+ \\d+")){
                String[] command = line.split(" ");
                hardSize = Integer.parseInt(command[0]);
                hardNum = Integer.parseInt(command[1]);
                break;
            }
            else {
                System.out.println("invalid command");
            }
        }

        for (int i = 0; i < hardNum; i++) {
            String name;
            int space;
            while (true){
                String line = Main.input.nextLine();
                line = line.trim();
                if (line.matches("\\S+ \\d+")){
                    String[] command = line.split(" ");
                    name = command[0];
                    space = Integer.parseInt(command[1]);
                    if (!isValidName(name)){
                        System.out.println("invalid name");
                    }
                    else if (sizeUntilNow + space > hardSize){
                        System.out.println("insufficient hard size");
                    }
                    else {
                        sizeUntilNow += space;
                        new Drive(space , name);
                        break;
                    }
                }
                else {
                    System.out.println("invalid command");
                }
            }

        }

    }

    private static boolean isValidName(String name){
        if (name.length() > 1)
            return false;
        if (name.charAt(0) < 'A' || name.charAt(0) > 'Z')
            return false;
        return getDriveByName(name) == null;
    }

    public static Drive getDriveByName(String name){
        for (Drive drive : allDrives) {
            if (drive.getName().equals(name))
                return drive;
        }
        return null;
    }

    public static void printDriveStatus(){
        for (Drive drive : allDrives) {
            System.out.println(drive.name + " " + drive.allSpace + "MB " + drive.usedSpace + "MB");
        }
    }
}
