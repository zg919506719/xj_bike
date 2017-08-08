package com.xingjian.xjmtkpad.inter;

import android.content.Context;
import android.posapi.PosApi;
import android.widget.TextView;
import android.widget.VideoView;

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

    VideoView getVideoView();
}
