package com.xingjian.xjmtkpad.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.StationReq;
import com.xingjian.xjmtkpad.beanresponse.CardLoginRes;
import com.xingjian.xjmtkpad.beanresponse.LoginRes;
import com.xingjian.xjmtkpad.beanresponse.NameRes;
import com.xingjian.xjmtkpad.beanresponse.StaffRecordRes;
import com.xingjian.xjmtkpad.beanresponse.StationRes;
import com.xingjian.xjmtkpad.beanresponse.TimeRes;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class StationActivity extends AppCompatActivity {
    @BindView(R.id.map)
    MapView map;

    private SocketClient client;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        ButterKnife.bind(this);
        //在activity执行onCreate时执行map.onCreate(savedInstanceState)，创建地图
        map.onCreate(savedInstanceState);
        client = MyApp.Client;
        locate();
        init();
        socketRegister();
    }

    private void init() {
//        String reqStr11 = "{\"siteId\":\"59\",\"cmd\":\"11\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\"}}";
        StationReq req = new StationReq();
        req.setSiteId("59");
        req.setCmd("11");
        req.setWay("1");
        req.setSn("0");
        StationReq.DataBean bean = new StationReq.DataBean();
        bean.setAddress("济南");
        req.setData(bean);
        client.sendString(JSONObject.toJSONString(req));
    }

    private void socketRegister() {
        client.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {
            }

            @Override
            public void onDisconnected(SocketClient client) {
                client.connect();
            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                String message = responsePacket.getMessage();
                //获取服务器时间
                if (message.contains("\"cmd\":\"11\"")) {
                    StationRes stationRes = JSONObject.parseObject(message, StationRes.class);
                    StationRes.DataBean data = stationRes.getData();
                    if (!data.getCount().equals("0")) {
                        Iterator<StationRes.DataBean.ListBean> iterator = data.getList().iterator();
                        while (iterator.hasNext()) {
                            StationRes.DataBean.ListBean bean = iterator.next();
                            /**
                             * site_id : 59
                             * site_name : 历下区政府
                             * site_address : 济南
                             * site_addressLongitude : 117.083021,36.672009
                             */
                            String site_addressLongitude = bean.getSite_addressLongitude();
                            Log.i("haha", site_addressLongitude);
                            String[] split = site_addressLongitude.split(",");
                            LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                            aMap.addMarker(new MarkerOptions().position(latLng).title(bean.getSite_address()).snippet(bean.getSite_name()));
                        }

                    }
                }
            }
        });
    }

    private void locate() {
        // 设置定位监听
        aMap = this.map.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(6));
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮

        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置

        uiSettings.setScaleControlsEnabled(true);//控制比例尺控件是否显示
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.i("haha", location.getLatitude() + "..." + location.getLongitude());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行map.onDestroy()，销毁地图
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行map.onResume ()，重新绘制加载地图
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行map.onPause ()，暂停地图的绘制
        map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行map.onSaveInstanceState (outState)，保存地图当前的状态
        map.onSaveInstanceState(outState);
    }
}
