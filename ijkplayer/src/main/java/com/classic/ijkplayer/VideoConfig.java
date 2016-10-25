package com.classic.ijkplayer;

import com.classic.ijkplayer.widget.IjkVideoView;

public class VideoConfig {
    private VideoConfig() {
        initDefault();
    }

    private static volatile VideoConfig sVideoConfig;

    public static VideoConfig getInstance() {
        if (null == sVideoConfig) {
            synchronized (VideoConfig.class) {
                if (null == sVideoConfig) {
                    sVideoConfig = new VideoConfig();
                }
            }
        }
        return sVideoConfig;
    }

    public static final int PLAYER_AUTO                = 0;
    public static final int PLAYER_ANDROID_MEDIAPLAYER = 1;
    public static final int PLAYER_IJK_MEDIAPLAYER     = 2;
    public static final int PLAYER_IJKEXO_MEDIAPLAYER  = 3;

    public static final int RENDER_SURFACE_VIEW = IjkVideoView.RENDER_SURFACE_VIEW;
    public static final int RENDER_TEXTURE_VIEW = IjkVideoView.RENDER_TEXTURE_VIEW;
    public static final int RENDER_NONE         = IjkVideoView.RENDER_NONE;

    public static final String FORMAT_AUTO       = "";
    public static final String FORMAT_RGB_565    = "fcc-rv16";
    public static final String FORMAT_RGB_888    = "fcc-rv24";
    public static final String FORMAT_RGBX_8888  = "fcc-yv32";
    public static final String FORMAT_YV12       = "fcc-yv12";
    public static final String FORMAT_OPENGL_ES2 = "fcc-_es2";

    private void initDefault() {
        mMediaPlayerImpl = PLAYER_IJK_MEDIAPLAYER;
        mRender = RENDER_SURFACE_VIEW;
        mPixelFormat = FORMAT_AUTO;
    }

    private int mMediaPlayerImpl; //播放实现类

    private int mRender; //呈现视图

    private boolean mEnableMediaCodec;

    private boolean mEnableMediaCodecAutoRotate;
    private boolean mEnableOpenSLES;

    private String mPixelFormat;

    private boolean mEnableDetachedSurfaceTextureView;
    private boolean mEnableBackgroundPlay;

    public int getMediaPlayerImpl() {
        return mMediaPlayerImpl;
    }

    public void setMediaPlayerImpl(int mediaPlayerImpl) {
        mMediaPlayerImpl = mediaPlayerImpl;
    }

    public int getRender() {
        return mRender;
    }

    public void setRender(int render) {
        mRender = render;
    }

    public boolean isEnableMediaCodec() {
        return mEnableMediaCodec;
    }

    public void setEnableMediaCodec(boolean enableMediaCodec) {
        mEnableMediaCodec = enableMediaCodec;
    }

    public boolean isEnableMediaCodecAutoRotate() {
        return mEnableMediaCodecAutoRotate;
    }

    public void setEnableMediaCodecAutoRotate(boolean enableMediaCodecAutoRotate) {
        mEnableMediaCodecAutoRotate = enableMediaCodecAutoRotate;
    }

    public boolean isEnableOpenSLES() {
        return mEnableOpenSLES;
    }

    public void setEnableOpenSLES(boolean enableOpenSLES) {
        mEnableOpenSLES = enableOpenSLES;
    }

    public String getPixelFormat() {
        return mPixelFormat;
    }

    public void setPixelFormat(String pixelFormat) {
        mPixelFormat = pixelFormat;
    }

    public boolean isEnableDetachedSurfaceTextureView() {
        return mEnableDetachedSurfaceTextureView;
    }

    public void setEnableDetachedSurfaceTextureView(boolean enableDetachedSurfaceTextureView) {
        mEnableDetachedSurfaceTextureView = enableDetachedSurfaceTextureView;
    }

    public boolean isEnableBackgroundPlay() {
        return mEnableBackgroundPlay;
    }

    public void setEnableBackgroundPlay(boolean enableBackgroundPlay) {
        mEnableBackgroundPlay = enableBackgroundPlay;
    }

}
