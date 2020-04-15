import java.util.ArrayList;

public class CommandProcess {
    static Directory position = Drive.allDrives.get(0);
    static ArrayList<File> copiedFiles = new ArrayList<>();
    static ArrayList<Folder> copiedFolders = new ArrayList<>();
    static boolean cutted = false;

    public static void commandProcessor() throws CloneNotSupportedException {

        while (true){
            String line = Main.input.nextLine();
            line = line.trim();
            if (line.equals("end")){
                break;
            }

            else if (line.equals("print OS information")){
               OS.printOSInfo();
            }

            else if (line.equals("print drives status")) {
                Drive.printDriveStatus();
            }

            else if (line.matches("go to drive \\S+")){
                String driveName = line.split(" ")[3];
                if (Drive.getDriveByName(driveName) == null){
                    System.out.println("invalid name");
                }
                else {
                    position = Drive.getDriveByName(driveName);
                }
            }

            else if (line.equals("back")){
                position = position.goBack();
            }

            else if (line.matches("create folder \\S+")){
                String name = line.split(" ")[2];
                if (position.getFolderByName(name) != null){
                    System.out.println("folder exists with this name");
                }
                else {
                    position.createFolder(name);
                    System.out.println("folder created");
                }
            }

            else if (line.matches("open \\S+")){
                String name = line.split(" ")[1];
                if (position.getFolderByName(name) == null){
                    System.out.println("invalid name");
                }
                else {
                    position = position.getFolderByName(name);
                    position.openedOnce();
                }
            }

            else if (line.matches("delete folder \\S+")){
                String name = line.split(" ")[2];
                if (position.getFolderByName(name) == null){
                    System.out.println("invalid name");
                }
                else {
                    position.deleteFolder(name);
                    System.out.println("folder deleted");
                }
            }

            else if (line.matches("rename folder \\S+ \\S+")){
                String lastName = line.split(" ")[2];
                String newName = line.split(" ")[3];
                if (position.getFolderByName(lastName) == null){
                    System.out.println("invalid name");
                }
                else if (position.getFolderByName(newName) != null){
                    System.out.println("folder exists with this name");
                }
                else {
                    position.getFolderByName(lastName).setName(newName);
                    System.out.println("folder renamed");
                }
            }

            else if (line.equals("status")) {
                position.printStatus();
            }

            else if (line.matches("create file \\S+ \\S+ \\d+")){
                String name = line.split(" ")[2];
                String format = line.split(" ")[3];
                int size = Integer.parseInt(line.split(" ")[4]);
                if (position.getFileByName(name) != null){
                    System.out.println("file exists with this name");
                }
                else if (!format.matches("(img|mp4|txt)")){
                    System.out.println("invalid format");
                }
                else if (position.root.usedSpace + size > position.root.getAllSpace()){
                    System.out.println("insufficient drive size");
                }
                else {
                    position.createFile(name , format , size);
                    System.out.println("file created");
                }
            }

            else if (line.matches("rename file \\S+ \\S+")){
                String lastName = line.split(" ")[2];
                String newName = line.split(" ")[3];
                if (position.getFileByName(lastName) == null){
                    System.out.println("invalid name");
                }
                else if (position.getFileByName(newName) != null){
                    System.out.println("file exists with this name");
                }
                else {
                    position.getFileByName(lastName).setName(newName);
                    System.out.println("file renamed");
                }
            }

            else if (line.matches("delete file \\S+")){
                String name = line.split(" ")[2];
                if (position.getFileByName(name) == null){
                    System.out.println("invalid name");
                }
                else {
                    position.deleteFile(name);
                    System.out.println("file deleted");
                }
            }

            else if (line.matches("write text \\S+")){
                String name = line.split(" ")[2];
                File file = position.getFileByName(name);
                if (file == null){
                    System.out.println("invalid name");
                }
                else if (file instanceof Text){
                    String newText = Main.input.nextLine();
                    ((Text) file).setTxt(newText);
                }
                else{
                    System.out.println("this file is not a text file");
                }
            }

            else if (line.matches("print file stats \\S+")){
                String name = line.split(" ")[3];
                if (position.getFileByName(name) == null){
                    System.out.println("invalid name");
                }
                else {
                    position.getFileByName(name).printStatus();
                }
            }

            else if (line.equals("print frequent folders")) {
                Folder.printFrequentFolder();
            }

            else if (line.matches("copy file .+")){
                if (copyFile(line)){
                    System.out.println("files copied");
                    cutted = false;
                }
                else {
                    System.out.println("invalid name");
                }
            }

            else if (line.equals("paste")){
                if (copiedFiles.size() > 0){
                    pasteCopiedFiles();
                }
                else if (copiedFolders.size() > 0){
                    pasteCopiedFolders();
                }
             }

            else if (line.matches("cut file .+")){
                if (copyFile(line)){
                    System.out.println("files cut completed");
                    cutted = true;
                }
                else {
                    System.out.println("invalid name");
                }
            }

            else if (line.matches("copy folder .+")){
                if (copyFolder(line)){
                    cutted = false;
                    System.out.println("folders copied");
                }
                else {
                    System.out.println("invalid name");
                }
            }

            else if (line.matches("cut folder .+")){
                if (copyFolder(line)){
                    cutted = true;
                    System.out.println("folders cut completed");
                }
                else {
                    System.out.println("invalid name");
                }
            }

            else {
                System.out.println("invalid command");
            }
        }
    }

    private static void pasteCopiedFolders() throws CloneNotSupportedException {
        int allSize = 0 ;
        boolean found = false;
        for (Folder folder : copiedFolders) {
            if (position.getFolderByName(folder.name) != null){
                found = true;
                break;
            }
            allSize += folder.usedSpace;
        }

        if (found){
            System.out.println("folder exists with this name");
        }
        else if (position.root.usedSpace + allSize > position.root.getAllSpace()){
            System.out.println("insufficient drive size");
        }
        else {
            for (Folder folder : copiedFolders) {
                Folder newFolder = (Folder) folder.clone(position.root) ;
                newFolder.parent = position;
                position.folders.add(newFolder);
            }

            ArrayList<Directory> allParent = position.getAllParent();
            for (Directory father : allParent) {
                father.usedSpace += allSize;
            }

            if (cutted){
                for (Folder folder : copiedFolders) {
                    folder.parent.deleteFolder(folder.name);
                    folder.seen = 0;
                }
            }
            copiedFiles = new ArrayList<>();
            copiedFolders = new ArrayList<>();

            System.out.println("paste completed");
        }
    }

    private static boolean copyFolder(String line){
        String[] command = line.split(" ");
        boolean allExist = true;

        for (int i = 2; i < command.length; i++) {
            if (position.getFolderByName(command[i]) == null)
            {
                allExist = false;
                break;
            }
        }

        if (allExist){
            copiedFiles = new ArrayList<>();
            copiedFolders = new ArrayList<>();

            for (int i = 2; i < command.length; i++) {
                copiedFolders.add(position.getFolderByName(command[i])) ;
            }
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean copyFile(String line){
        String[] command = line.split(" ");
        boolean allExist = true;
        for (int i = 2; i < command.length; i++) {
            if (position.getFileByName(command[i]) == null)
            {
                allExist = false;
                break;
            }
        }

        if (allExist){
            copiedFiles = new ArrayList<>();
            copiedFolders = new ArrayList<>();

            for (int i = 2; i < command.length; i++) {
                copiedFiles.add(position.getFileByName(command[i])) ;
            }
            return true;
        }
        else {
            return false;
        }
    }

    private static void pasteCopiedFiles() throws CloneNotSupportedException {
        int allSize = 0 ;
        boolean found = false;
        for (File file : copiedFiles) {
            if (position.getFileByName(file.name) != null){
                found = true;
                break;
            }
            allSize += file.usedSpace;
        }
        if (found){
            System.out.println("file exists with this name");
        }
        else if (position.root.usedSpace + allSize > position.root.getAllSpace()){
            System.out.println("insufficient drive size");
        }
        else {
            for (File file : copiedFiles) {
                File newFile = (File) file.clone() ;
                newFile.parent = position;
                position.files.add(newFile);
            }

            ArrayList<Directory> allParent = position.getAllParent();
            for (Directory father : allParent) {
                father.usedSpace += allSize;
            }


            if (cutted){
                for (File file : copiedFiles) {
                    file.parent.deleteFile(file.name);
                }
            }

            copiedFiles = new ArrayList<>();
            copiedFolders = new ArrayList<>();

            System.out.println("paste completed");
        }

    }


}
