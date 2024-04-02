package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.interfaces;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.facebook_model.NodeModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.TrayModel;

public interface UserListInterface {

    void userListClick(int i, TrayModel trayModel);

    void fbUserListClick(int position, NodeModel nodeModel);
}