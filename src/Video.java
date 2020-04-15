import java.util.ArrayList;

public class Video extends File{
    private String quality;
    private String videoLength;

    public Video(String name, Directory parent, int usedSpace, String quality, String videolength) {
        super(name, parent, usedSpace);
        this.quality = quality;
        this.videoLength = videolength;
    }

    @Override
    public void printStatus() {

        System.out.println(this.name + " mp4");
        super.printStatus();
        System.out.println("Quality: " + this.quality);
        System.out.println("Video Length: " + this.videoLength);
    }
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}

