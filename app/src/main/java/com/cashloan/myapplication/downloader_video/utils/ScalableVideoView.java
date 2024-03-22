package com.cashloan.myapplication.downloader_video.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class ScalableVideoView extends VideoView {
    private DisplayMode displayMode;
    private int mVideoHeight;
    private int mVideoWidth;

    public enum DisplayMode {
        ORIGINAL,
        FULL_SCREEN,
        ZOOM
    }

    public ScalableVideoView(Context context) {
        super(context);
        this.displayMode = DisplayMode.FULL_SCREEN;
    }

    public ScalableVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.displayMode = DisplayMode.FULL_SCREEN;
    }

    public ScalableVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.displayMode = DisplayMode.FULL_SCREEN;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }

    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int defaultSize = getDefaultSize(0, i);
        int defaultSize2 = getDefaultSize(this.mVideoHeight, i2);
        if (this.displayMode == DisplayMode.ORIGINAL) {
            int i6 = this.mVideoWidth;
            if (i6 > 0 && (i5 = this.mVideoHeight) > 0) {
                if (i6 * defaultSize2 > defaultSize * i5) {
                    defaultSize2 = (i5 * defaultSize) / i6;
                } else if (i6 * defaultSize2 < defaultSize * i5) {
                    defaultSize = (i6 * defaultSize2) / i5;
                }
            }
        } else if (this.displayMode != DisplayMode.FULL_SCREEN && this.displayMode == DisplayMode.ZOOM && (i3 = this.mVideoWidth) > 0 && (i4 = this.mVideoHeight) > 0 && i3 < defaultSize) {
            defaultSize2 = (i4 * defaultSize) / i3;
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    public void changeVideoSize(int i, int i2) {
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
        getHolder().setFixedSize(i, i2);
        requestLayout();
        invalidate();
    }

    public void setDisplayMode(DisplayMode displayMode2) {
        this.displayMode = displayMode2;
    }
}
