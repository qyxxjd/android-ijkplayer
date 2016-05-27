package com.classic.ijkplayer.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.classic.ijkplayer.VideoActivity;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoActivity.start(this,"http://gslb.miaopai.com/stream/oxX3t3Vm5XPHKUeTS-zbXA__.mp4", "这里写标题");
    }
}
