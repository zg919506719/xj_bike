package com.xingjian.xjmtkpad.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class StringDialog {
    public void showString(Context context, String data) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_string, null);
        Button sure = (Button) view.findViewById(R.id.btn_sure);
        TextView tv_data = (TextView) view.findViewById(R.id.tv_data);
        tv_data.setText(data);
        dialog.setView(view);
        final AlertDialog show = dialog.show();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }
}
