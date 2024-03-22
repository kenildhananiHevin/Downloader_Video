package com.cashloan.myapplication.downloader_video;

import org.json.JSONObject;

public class ObjectJson {
    private JSONObject jsonObject;

    public ObjectJson(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
