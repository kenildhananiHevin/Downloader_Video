package com.cashloan.myapplication.downloader_video.model.facebook_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NodeModel implements Serializable {
    @SerializedName("node")
    private NodeDataModel nodeDataModel;
    String pack;

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public NodeDataModel getNodeDataModel() {
        return this.nodeDataModel;
    }

    public void setNodeDataModel(NodeDataModel nodeDataModel2) {
        this.nodeDataModel = nodeDataModel2;
    }
}
