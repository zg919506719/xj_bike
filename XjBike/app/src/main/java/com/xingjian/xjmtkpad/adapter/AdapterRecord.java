package com.xingjian.xjmtkpad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.beanresponse.StaffRecordRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinkpad on 2017/8/18.
 */

public class AdapterRecord extends BaseAdapter {
    ArrayList<StaffRecordRes.DataBean.ListBean> data;

    public AdapterRecord(ArrayList<StaffRecordRes.DataBean.ListBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_record, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        StaffRecordRes.DataBean.ListBean bean = data.get(i);
//        attendance_type 考勤记录类型（1-签到，2-签退）
//        attendance_time 考勤时间
        if (bean.getOperate_type().equals("1")) {
            viewHolder.type.setText("签到");
        } else if (bean.getOperate_type().equals("2")) {
            viewHolder.type.setText("签退");
        }
        viewHolder.time.setText(bean.getAttendance_time());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.type)
        TextView type;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
