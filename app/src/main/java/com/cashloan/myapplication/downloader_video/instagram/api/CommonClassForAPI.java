package com.cashloan.myapplication.downloader_video.instagram.api;

import android.app.Activity;
import android.util.Base64;

import com.cashloan.myapplication.downloader_video.model.insta_story.FullDetailModel;
import com.cashloan.myapplication.downloader_video.model.insta_story.StoryModel;
import com.cashloan.myapplication.downloader_video.other.CommonClass;
import com.google.gson.JsonObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CommonClassForAPI {

    private static Activity mactivity;

    private String webKitKeysFun(String str, String str2) {
        if (str2 == null || str2.isEmpty() || str2.equalsIgnoreCase("null") || str2.equalsIgnoreCase("0")) {
            if (str.contains("/tv/")) {
                return "Instagram 128.0.0.19.128 (Linux; Android 8.0; ANE-LX1 Build/HUAWEIANE-LX1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.109 Mobile Safari/537.36";
            } else {
                return "Mozilla/5.0 (Linux; U; Android 4.2.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
            }
        }
        return str2;
    }

    public void callResult(DisposableObserver<JsonObject> disposableObserver, String str, String modifiedStr2) {
        if (modifiedStr2 == null || modifiedStr2.isEmpty()) {
            modifiedStr2 = "";
        }
        webKitKeysFun(str, modifiedStr2);

        InstagramRestClient.getInstance(mactivity).getService().callResult(str, modifiedStr2, webKitKeysFun(str, modifiedStr2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        disposableObserver.onNext(jsonObject);
                    }

                    @Override
                    public void onError(Throwable th) {
                        disposableObserver.onError(th);
                    }
                });
    }

    private static com.cashloan.myapplication.downloader_video.instagram.api.CommonClassForAPI CommonClassForAPI;

    public static com.cashloan.myapplication.downloader_video.instagram.api.CommonClassForAPI getInstance() {
        CommonClassForAPI = new CommonClassForAPI();
        return CommonClassForAPI;
    }

    public void getStories(final DisposableObserver disposableObserver, String str) {
        if (CommonClass.isNullOrEmpty(str)) {
            str = "";
        }
        InstagramRestClient.getInstance(mactivity).getService().getStoriesApi(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu42WcpscR/vYfeBAs6heZq3l"), str, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StoryModel>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(StoryModel model_story) {
                disposableObserver.onNext(model_story);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }


 /*   public void getStories(final DisposableObserver disposableObserver, String str) {
        if (CommonClass.isNullOrEmpty(str)) {
            str = "";
        }
        InstagramRestClient.getInstance(mactivity).getService().getStoriesApi("https://i.instagram.com/api/v1/feed/reels_tray/", str, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StoryModel>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(StoryModel storyModel) {
                disposableObserver.onNext(storyModel);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }*/

    public void getFullDetailFeed(final DisposableObserver disposableObserver, String str, String str2) {
        InstagramRestClient.getInstance(mactivity).getService().getFullDetailInfoApi(a("nkBz6UCOMjENvG2xDD9iec5uFKnFUc9ElRjyJsyvu422kijZtqsvkNfEDe4Jqshk/DIAuxF3zpe9OgIpknIHAw==") + str, str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FullDetailModel>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(FullDetailModel model_full_detail) {
                disposableObserver.onNext(model_full_detail);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public static String f11252a = "aesEncryptionKey";

    public static String f11253b = "encryptionIntVec";

    public static String a(String str) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(f11253b.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(f11252a.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return new String(cipher.doFinal(Base64.decode(str, 0)));
        } catch (Exception e10) {
            e10.printStackTrace();
            return null;
        }
    }

/*
    public void getFullDetailFeed(final DisposableObserver disposableObserver, String str, String str2) {
        InstagramAPIInterface service = InstagramRestClient.getInstance(mactivity).getService();
        service.getFullDetailInfoApi("https://i.instagram.com/api/v1/users/" + str + "/full_detail_info?max_id=", str2, "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FullDetailModel>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(FullDetailModel fullDetailModel) {
                disposableObserver.onNext(fullDetailModel);
            }

            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }
*/
}
