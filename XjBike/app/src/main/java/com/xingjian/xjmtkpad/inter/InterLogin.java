package com.xingjian.xjmtkpad.inter;

import android.content.Context;
import android.posapi.PosApi;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.xingjian.xjmtkpad.view.MyVideoView;

/**
 * Created by thinkpad on 2017/8/2.
 */

public interface InterLogin {
    Context getContext();

    PosApi getApi();

    TextView getTime();

    TextView getTemp();

    TextView getHumidity();

    TextView getDevice();

    TextView getLocation();

    //    VideoView getVideoView();
    MyVideoView getVideoView();

    SeekBar getSeekBar();
}
