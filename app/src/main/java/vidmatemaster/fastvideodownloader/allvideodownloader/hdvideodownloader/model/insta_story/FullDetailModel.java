package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FullDetailModel implements Serializable {
/*    @SerializedName("reels_media")
    private ReelFeedModel reel_feed;*/

    @SerializedName("reels_media")
    private ArrayList<ReelFeedModel> reel_feed;

    @SerializedName("user_detail")
    private UserDetailModel user_detail;

    public UserDetailModel getUser_detail() {
        return this.user_detail;
    }

    public void setUser_detail(UserDetailModel userDetailModel) {
        this.user_detail = userDetailModel;
    }

    public ArrayList<ReelFeedModel> getReel_feed() {
        return this.reel_feed;
    }

    public void setReel_feed(ArrayList<ReelFeedModel> model_reelfeed) {
        this.reel_feed = model_reelfeed;
    }

  /*  public ReelFeedModel getReel_feed() {
        return this.reel_feed;
    }

    public void setReel_feed(ReelFeedModel reelFeedModel) {
        this.reel_feed = reelFeedModel;
    }*/
}