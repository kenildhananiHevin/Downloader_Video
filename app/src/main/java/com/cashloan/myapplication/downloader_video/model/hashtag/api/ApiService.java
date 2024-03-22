
package com.cashloan.myapplication.downloader_video.model.hashtag.api;

import com.cashloan.myapplication.downloader_video.model.hashtag.Root;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/hastag")
    Call<Root> getHashtags(@Query("keyword") String keyword);
}
