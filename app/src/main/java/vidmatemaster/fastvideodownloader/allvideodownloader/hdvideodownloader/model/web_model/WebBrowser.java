package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.web_model;

public class WebBrowser {

    String name;
    String url;
    int image;

    public WebBrowser(String name, String url, int image) {
        this.name = name;
        this.url = url;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
