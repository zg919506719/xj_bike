package com.xingjian.xjmtkpad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.beanresponse.CardInfoRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class AdapterCard extends BaseAdapter {
    private ArrayList<CardInfoRes.DataBean.ListBean> list;

    public AdapterCard(ArrayList<CardInfoRes.DataBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_card, viewGroup, false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        CardInfoRes.DataBean.ListBean bean = list.get(i);
        holder.type.setText(bean.getOperate_type());
        holder.time.setText(bean.getOperates_time());
            String result = bean.getOperates_result();
        if (result.equals("1")) {
            holder.result.setText("成功");
        }else  if (result.equals("0")) {
            holder.result.setText("失败");
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.result)
        TextView result;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
