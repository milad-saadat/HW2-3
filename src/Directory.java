import java.util.ArrayList;

public class Directory {
    protected Directory parent;
    protected int usedSpace;
    protected String name;
    protected Drive root;
    protected ArrayList<Folder> folders = new ArrayList<>();
    protected ArrayList<File> files = new ArrayList<>();
    protected int seen=0;

    public Directory goBack(){
        return this.parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Folder getFolderByName(String name){
        for (Folder folder : folders) {
            if (folder.name.equalsIgnoreCase(name)){
                return folder;
            }
        }
        return null;
    }

    public void createFolder(String name){
        Folder folder = new Folder(name , this , this.root);
        folders.add(folder);
    }

    public void openedOnce() {
        seen++;
    }

    public ArrayList<Directory> getAllParent(){
        ArrayList<Directory> res = new ArrayList<>();
        Directory pos = this;
        while (pos != pos.parent){
            res.add(pos);
            pos = pos.parent;
        }
        res.add(pos);
        return res;
    }

    public void deleteFolder(String name){
        Folder folder = this.getFolderByName(name);
        this.folders.remove(folder);
        ArrayList<Directory> allParent = this.getAllParent();
        for (Directory father : allParent) {
            father.usedSpace -= folder.usedSpace;
        }
    }

    public void printStatus(){
        StringBuilder path = getPath();
        System.out.println(path);
        System.out.println("Folders:");
        folders.sort(Folder::compareTo);
        for (Folder folder : folders) {
            System.out.println(folder.name + " " + folder.usedSpace + "MB");
        }

        ArrayList<Image> images = new ArrayList<>();
        ArrayList<Video> videos = new ArrayList<>();
        ArrayList<Text> texts = new ArrayList<>();
        files.sort(File::compareTo);

        for (File file : files) {
            if (file instanceof Image)
                images.add((Image) file);
            if (file instanceof Video)
                videos.add((Video) file);
            if (file instanceof Text)
                texts.add((Text) file);
        }

        System.out.println("Files:");

        for (Image image : images) {
            System.out.println(image.name + " img " + image.usedSpace+"MB");
        }
        for (Text text : texts) {
            System.out.println(text.name + " txt " + text.usedSpace+"MB");
        }
        for (Video video : videos) {
            System.out.println(video.name + " mp4 " + video.usedSpace+"MB");
        }


    }

    public StringBuilder getPath(){
        ArrayList<Directory> allParent = getAllParent();
        StringBuilder path = new StringBuilder() ;
        for (int i=0 ; i<allParent.size()-1 ; i++) {
            path.insert(0 , "\\" + allParent.get(i).name);
        }
        path.insert(0 , allParent.get(allParent.size()-1).name + ":");
        return path;
    }

    public File getFileByName(String name){
        for (File file : files) {
            if (file.name.equalsIgnoreCase(name))
                return file;
        }
        return null;
    }

    public void createFile(String name, String format , int size){
        if (format.equals("mp4")){
            System.out.println("Quality:");
            String quality = Main.input.nextLine();
            System.out.println("Video Length:");
            String videolength = Main.input.nextLine();
            files.add(new Video(name , this , size, quality, videolength));
        }
        else if (format.equals("txt")){
            System.out.println("Text:");
            String txt = Main.input.nextLine();
            files.add(new Text(name , this , size , txt));
        }
        else {
            System.out.println("Resolution:");
            String resolution = Main.input.nextLine();
            System.out.println("Extension:");
            String extension = Main.input.nextLine();
            files.add(new Image(name , this , size , resolution , extension));
        }

        ArrayList<Directory> allParent = getAllParent();

        for (Directory father : allParent) {
            father.usedSpace+=size;
        }

    }

    public void deleteFile(String name) {
        File file = this.getFileByName(name);
        this.files.remove(file);
        ArrayList<Directory> allParent = this.getAllParent();
        for (Directory father : allParent) {
            father.usedSpace -= file.usedSpace;
        }
        file.parent = null;
    }
}
