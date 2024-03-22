package com.cashloan.myapplication.downloader_video.instagram.api;

import com.cashloan.myapplication.downloader_video.model.insta_story.FullDetailModel;
import com.cashloan.myapplication.downloader_video.model.insta_story.StoryModel;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public interface InstagramAPIInterface {
    @GET
    Observable<JsonObject> callResult(
            @Url String url,
            @Header("Cookie") String cookie,
            @Header("User-Agent") String userAgent
    );
    @GET
    Observable<StoryModel> getStoriesApi(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);

    @GET
    Observable<FullDetailModel> getFullDetailInfoApi(@Url String str, @Header("Cookie") String str2, @Header("User-Agent") String str3);
}
