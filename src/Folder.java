import java.util.ArrayList;

public class Folder extends Directory implements Cloneable{
    public static ArrayList<Folder> allFolders = new ArrayList<>();

    public Folder(String name , Directory parent , Drive root) {
        this.name = name;
        this.parent = parent;
        this.root = root;
        allFolders.add(this);
    }

    public int compareTo(Folder other){
        return this.name.compareTo(other.name);
    }

    public int compareForFrequent(Folder other){
        if (other.seen != this.seen){
            return (this.seen < other.seen ? 1 : -1);
        }
        return  (this.getPath().toString()).compareTo(other.getPath().toString()) ;
    }

    public static void printFrequentFolder(){
        allFolders.sort(Folder::compareForFrequent);
        for (int i = 0; i < Math.min(5 , allFolders.size()); i++) {
            Folder folder = allFolders.get(i);
            if (folder.seen == 0)
                break;
            System.out.println(folder.getPath() + " " + folder.seen);
        }
    }

    protected Object clone(Drive allRoot) throws CloneNotSupportedException {
        Folder resFolder = (Folder) super.clone();
        resFolder.files = new ArrayList<>();
        resFolder.folders = new ArrayList<>();

        for (File file : this.files) {
            File newFile = (File) file.clone();
            newFile.parent = resFolder;
            resFolder.files.add(newFile);
        }

        for (Folder folder : this.folders) {
            Folder newFolder = (Folder) folder.clone(allRoot);
            newFolder.parent = resFolder;
            resFolder.folders.add(newFolder);
        }

        resFolder.root = allRoot;
        resFolder.seen = 0;
        allFolders.add(resFolder);
        return resFolder;
    }
}
