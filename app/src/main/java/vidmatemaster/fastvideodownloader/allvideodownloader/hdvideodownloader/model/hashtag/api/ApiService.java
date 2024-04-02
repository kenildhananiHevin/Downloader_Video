
package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag.api;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/hastag")
    Call<Root> getHashtags(@Query("keyword") String keyword);
}
