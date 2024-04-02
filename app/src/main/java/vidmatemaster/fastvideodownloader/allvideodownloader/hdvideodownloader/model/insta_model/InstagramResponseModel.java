package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InstagramResponseModel {
    @SerializedName("graphql")
    private GraphqlInsta graphql;

    public GraphqlInsta getGraphql() {
        return graphql;
    }

    public void setGraphql(GraphqlInsta graphql) {
        this.graphql = graphql;
    }

    public static class GraphqlInsta implements Serializable {
        @SerializedName("shortcode_media")
        private ViewMedia shortcodeMedia;

        public ViewMedia getShortcodeMedia() {
            return shortcodeMedia;
        }

        public void setShortcodeMedia(ViewMedia shortcodeMedia) {
            this.shortcodeMedia = shortcodeMedia;
        }

        public static class ViewMedia implements Serializable {
            @SerializedName("accessibility_caption")
            private String accessibilityCaption;

            @SerializedName("display_resources")
            private List<DisplayResource> displayResources;

            @SerializedName("display_url")
            private String displayUrl;

            @SerializedName("edge_sidecar_to_children")
            private ApplyChildView edgeSidecarToChildren;

            @SerializedName("is_video")
            private boolean isVideo;

            @SerializedName("video_url")
            private String videoUrl;

            public String getAccessibilityCaption() {
                return accessibilityCaption;
            }

            public void setAccessibilityCaption(String accessibilityCaption) {
                this.accessibilityCaption = accessibilityCaption;
            }

            public List<DisplayResource> getDisplayResources() {
                return displayResources;
            }

            public void setDisplayResources(List<DisplayResource> displayResources) {
                this.displayResources = displayResources;
            }

            public String getDisplayUrl() {
                return displayUrl;
            }

            public void setDisplayUrl(String displayUrl) {
                this.displayUrl = displayUrl;
            }

            public ApplyChildView getEdgeSidecarToChildren() {
                return edgeSidecarToChildren;
            }

            public void setEdgeSidecarToChildren(ApplyChildView edgeSidecarToChildren) {
                this.edgeSidecarToChildren = edgeSidecarToChildren;
            }

            public boolean isVideo() {
                return isVideo;
            }

            public void setVideo(boolean video) {
                isVideo = video;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }
        }
    }

    public static class ApplyChildView implements Serializable {
        @SerializedName("edges")
        private List<Edge> edges;

        public List<Edge> getEdges() {
            return edges;
        }

        public void setEdges(List<Edge> edges) {
            this.edges = edges;
        }

        public static class Edge implements Serializable {
            @SerializedName("node")
            private ViewNormalSerial node;

            public ViewNormalSerial getNode() {
                return node;
            }

            public void setNode(ViewNormalSerial node) {
                this.node = node;
            }

            public static class ViewNormalSerial implements Serializable {
                @SerializedName("display_url")
                private String displayUrl;

                @SerializedName("is_video")
                private boolean isVideo;

                @SerializedName("video_url")
                private String videoUrl;

                @SerializedName("display_resources")
                private List<DisplayResource> displayResources;

                public String getDisplayUrl() {
                    return displayUrl;
                }

                public void setDisplayUrl(String displayUrl) {
                    this.displayUrl = displayUrl;
                }

                public boolean isVideo() {
                    return isVideo;
                }

                public void setVideo(boolean video) {
                    isVideo = video;
                }

                public String getVideoUrl() {
                    return videoUrl;
                }

                public void setVideoUrl(String videoUrl) {
                    this.videoUrl = videoUrl;
                }

                public List<DisplayResource> getDisplayResources() {
                    return displayResources;
                }

                public void setDisplayResources(List<DisplayResource> displayResources) {
                    this.displayResources = displayResources;
                }
            }
        }
    }

    public static class DisplayResource implements Serializable {
        @SerializedName("config_height")
        private int configHeight;

        @SerializedName("config_width")
        private int configWidth;

        @SerializedName("src")
        private String src;

        public int getConfigHeight() {
            return configHeight;
        }

        public void setConfigHeight(int configHeight) {
            this.configHeight = configHeight;
        }

        public int getConfigWidth() {
            return configWidth;
        }

        public void setConfigWidth(int configWidth) {
            this.configWidth = configWidth;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }

}
