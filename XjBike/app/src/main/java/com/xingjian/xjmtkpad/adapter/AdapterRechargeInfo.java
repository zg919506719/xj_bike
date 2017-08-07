package com.xingjian.xjmtkpad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.beanresponse.RechargeRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinkpad on 2017/8/6.
 */

public class AdapterRechargeInfo extends BaseAdapter {
    private ArrayList<RechargeRes.DataBean.ListBean> data;

    public AdapterRechargeInfo(ArrayList<RechargeRes.DataBean.ListBean> data) {
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
        RechargeRes.DataBean.ListBean bean = data.get(i);
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recharge, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String text = (i + 1) + "";
        holder.tvNumber.setText(text);
        holder.tvRechargetime.setText(bean.getRecharge_time());
        holder.tvRechargenum.setText(bean.getRecharge_cost());
        holder.tvCardmoney.setText(bean.getBalance());
        holder.tvRechargesite.setText(bean.getCustomer_id());
        holder.tvClientnumber.setText(bean.getStaff_id());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_rechargetime)
        TextView tvRechargetime;
        @BindView(R.id.tv_rechargenum)
        TextView tvRechargenum;
        @BindView(R.id.tv_cardmoney)
        TextView tvCardmoney;
        @BindView(R.id.tv_rechargesite)
        TextView tvRechargesite;
        @BindView(R.id.tv_clientnumber)
        TextView tvClientnumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
