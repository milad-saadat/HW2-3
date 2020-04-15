import java.util.ArrayList;

public class Image extends File {
    String resolution;
    String extension;

    public Image(String name, Directory parent, int usedSpace, String resolution, String extension) {
        super(name, parent, usedSpace);
        this.resolution = resolution;
        this.extension = extension;
    }

    @Override
    public void printStatus() {
        System.out.println(this.name + " img");
        super.printStatus();
        System.out.println("Resolution: " + this.resolution);
        System.out.println("Extension: " + this.extension);
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
