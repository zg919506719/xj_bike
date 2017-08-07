package com.xingjian.xjmtkpad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.beanresponse.RentRecordsRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinkpad on 2017/8/6.
 */

public class AdapterRentInfo extends BaseAdapter {
    private ArrayList<RentRecordsRes.DataBean.ListBean> data;

    public AdapterRentInfo(ArrayList<RentRecordsRes.DataBean.ListBean> data) {
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
        RentRecordsRes.DataBean.ListBean bean = data.get(i);
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_rent_info, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvRenttime.setText(bean.getBorrowCar_time());
        holder.tvReturntime.setText(bean.getRepayCar_time());
        holder.tvTimelong.setText(bean.getDuration());
        holder.tvMoney.setText(bean.getDeduction());
        holder.tvSitenumber.setText(bean.getSite_id());
        holder.tvLocknumber.setText(bean.getPile_id());
        holder.tvBikenumber.setText(bean.getVehicle_id());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_renttime)
        TextView tvRenttime;
        @BindView(R.id.tv_returntime)
        TextView tvReturntime;
        @BindView(R.id.tv_timelong)
        TextView tvTimelong;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_sitenumber)
        TextView tvSitenumber;
        @BindView(R.id.tv_locknumber)
        TextView tvLocknumber;
        @BindView(R.id.tv_bikenumber)
        TextView tvBikenumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
