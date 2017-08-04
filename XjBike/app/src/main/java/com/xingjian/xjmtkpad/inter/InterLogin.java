package com.xingjian.xjmtkpad.inter;

import android.content.Context;
import android.posapi.PosApi;
import android.widget.TextView;

/**
 * Created by thinkpad on 2017/8/2.
 */

public interface InterLogin {
    Context getContext();

    PosApi getApi();

    TextView getTime();

    TextView getTemp();

    TextView getDevice();

    TextView getLocation();

    TextView getRentDay();

    TextView getRentHour();
}
