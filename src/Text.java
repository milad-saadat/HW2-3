import java.util.ArrayList;

public class Text extends File {
    String txt;

    public Text(String name, Directory parent, int usedSpace, String txt) {
        super(name, parent, usedSpace);
        this.txt = txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public void printStatus() {

        System.out.println(this.name + " txt");
        super.printStatus();
        System.out.println("Text: " + this.txt);
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
