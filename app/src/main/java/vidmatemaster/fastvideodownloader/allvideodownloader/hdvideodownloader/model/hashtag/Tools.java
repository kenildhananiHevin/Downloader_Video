package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag;

public class Tools {

    String name;
    int image;
    String nameId;

    public Tools(String name, int image,String nameId) {
        this.name = name;
        this.image = image;
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }
}
