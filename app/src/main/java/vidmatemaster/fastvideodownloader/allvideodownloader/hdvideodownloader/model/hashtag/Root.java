package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag;

import java.util.ArrayList;
import java.util.Date;

public class Root{
    public int status;
    public int id;
    public String keyword;
    public ArrayList<Hashtag> hashtags;
    public Date created_at;
    public Date updated_at;
    public int counter;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ArrayList<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}