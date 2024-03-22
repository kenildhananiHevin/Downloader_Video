package com.cashloan.myapplication.downloader_video.interfaces;

import com.cashloan.myapplication.downloader_video.model.facebook_model.NodeModel;
import com.cashloan.myapplication.downloader_video.model.insta_story.TrayModel;

public interface UserListInterface {

    void userListClick(int i, TrayModel trayModel);

    void fbUserListClick(int position, NodeModel nodeModel);
}