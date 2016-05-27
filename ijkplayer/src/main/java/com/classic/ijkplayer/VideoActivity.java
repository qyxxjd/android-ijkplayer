/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.classic.ijkplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.classic.ijkplayer.widget.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoActivity extends AppCompatActivity{
    private static final String PARAMS_VIDEO_PATH = "videoPath";
    private static final String PARAMS_VIDEO_URI = "videoUri";
    private static final String PARAMS_TITLE = "title";
    private static final String TAG = "VideoActivity";

    private String mTitle;
    private String mVideoPath;
    private Uri    mVideoUri;

    //private AndroidMediaController mMediaController;
    private VideoControllerView    mVideoControllerView;
    private IjkVideoView mVideoView;
    //private TextView     mToastTextView;
    //private TableLayout  mHudView;
    //private DrawerLayout mDrawerLayout;
    //private ViewGroup    mRightDrawer;

    private boolean mBackPressed;

    public static void start(Context context, String videoPath, String videoTitle) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(PARAMS_VIDEO_PATH, videoPath);
        intent.putExtra(PARAMS_TITLE, videoTitle);
        context.startActivity(intent);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        final Intent intent = getIntent();
        String intentAction = intent.getAction();
        if(intent.hasExtra(PARAMS_VIDEO_PATH)){
            mVideoPath = getIntent().getStringExtra(PARAMS_VIDEO_PATH);
            mTitle = getIntent().getStringExtra(PARAMS_TITLE);
        }else if (!TextUtils.isEmpty(intentAction)) {
            if (intentAction.equals(Intent.ACTION_VIEW)) {
                mVideoPath = intent.getDataString();
            } else if (intentAction.equals(Intent.ACTION_SEND)) {
                mVideoUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    String scheme = mVideoUri.getScheme();
                    if (TextUtils.isEmpty(scheme)) {
                        Log.e(TAG, "Null unknown ccheme\n");
                        finish();
                        return;
                    }
                    if (scheme.equals(ContentResolver.SCHEME_ANDROID_RESOURCE)) {
                        mVideoPath = mVideoUri.getPath();
                    } else if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                        Log.e(TAG, "Can not resolve content below Android-ICS\n");
                        finish();
                        return;
                    } else {
                        Log.e(TAG, "Unknown scheme " + scheme + "\n");
                        finish();
                        return;
                    }
                }
            }
        }

        mVideoControllerView = new VideoControllerView(this,false);
        // init UI
        Toolbar toolbar = (Toolbar) findViewById(R.id.video_toolbar);
        if(null != toolbar){
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            mVideoControllerView.setSupportActionBar(actionBar);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(TextUtils.isEmpty(mTitle)?"":mTitle);
        }

        //mMediaController = new AndroidMediaController(this, false);
        //mMediaController.setSupportActionBar(actionBar);

        //mToastTextView = (TextView) findViewById(R.id.toast_text_view);
        //mHudView = (TableLayout) findViewById(R.id.hud_view);
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mRightDrawer = (ViewGroup) findViewById(R.id.right_drawer);
        //mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mVideoControllerView);

        if (mVideoPath != null) {
            mVideoView.setVideoPath(mVideoPath);
        } else if (mVideoUri != null) {
            mVideoView.setVideoURI(mVideoUri);
        } else {
            Log.e(TAG, "Null Data Source\n");
            finish();
            return;
        }
        mVideoView.start();
        mVideoControllerView.setAnchorView(mVideoView);
        mVideoControllerView.setEnabled(true);
        mVideoControllerView.show();
    }

    @Override public void onBackPressed() {
        mBackPressed = true;

        super.onBackPressed();
    }

    @Override protected void onStop() {
        super.onStop();

        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
