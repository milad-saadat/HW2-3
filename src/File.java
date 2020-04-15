import java.util.ArrayList;

public class File implements Cloneable {
    protected String name;
    protected Directory parent;
    protected int usedSpace;

    public void setName(String name) {
        this.name = name;
    }

    public File(String name, Directory parent, int usedSpace) {
        this.name = name;
        this.parent = parent;
        this.usedSpace = usedSpace;
    }

    public int compareTo(File other){
        return this.name.compareTo(other.name);
    }

    public void printStatus(){
        StringBuilder path = this.parent.getPath();
        path.append("\\").append(this.name);
        System.out.println(path);

        System.out.println("Size: " + this.usedSpace + "MB");
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
