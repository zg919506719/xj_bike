package com.xingjian.xjmtkpad.utils;

import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketPacketHelper;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;
import com.xingjian.xjmtkpad.base.Constant;
import com.xingjian.xjmtkpad.base.MyUrl;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class MySocket {
    private SocketClient localSocketClient;

    public SocketClient getLocalSocketClient() {
        if (localSocketClient == null) {
            localSocketClient = new SocketClient();
            __i__setupAddress(localSocketClient);
            __i__setupEncoding(localSocketClient);
            __i__setupConstantHeartBeat(localSocketClient);
            __i__setupReadToTrailerForSender(localSocketClient);
            __i__setupReadToTrailerForReceiver(localSocketClient);
        }
        return localSocketClient;
    }

    private void __i__setupAddress(SocketClient socketClient) {
        socketClient.getAddress().setRemoteIP(MyUrl.IP); // 远程端IP地址
        socketClient.getAddress().setRemotePort(MyUrl.PORT); // 远程端端口号
        socketClient.getAddress().setConnectionTimeout(30 * 1000); // 连接超时时长，单位毫秒
    }

    private void __i__setupEncoding(SocketClient socketClient) {
        socketClient.setCharsetName(CharsetUtil.UTF_8); // 设置编码为UTF-8
    }

    private void __i__setupConstantHeartBeat(SocketClient socketClient) {
        /**
         * 设置自动发送的心跳包信息
         */
        socketClient.getHeartBeatHelper().setDefaultSendData(CharsetUtil.stringToData(Constant.HEAD, CharsetUtil.UTF_8));

        /**
         * 设置远程端发送到本地的心跳包信息内容，用于判断接收到的数据包是否是心跳包
         * 通过{@link SocketResponsePacket#isHeartBeat()} 查看数据包是否是心跳包
         */
        socketClient.getHeartBeatHelper().setDefaultReceiveData(CharsetUtil.stringToData(Constant.HEADRES, CharsetUtil.UTF_8));
        socketClient.getHeartBeatHelper().setHeartBeatInterval(30 * 1000); // 设置自动发送心跳包的间隔时长，单位毫秒
        socketClient.getHeartBeatHelper().setSendHeartBeatEnabled(true); // 设置允许自动发送心跳包，此值默认为false
    }

    private void __i__setupReadToTrailerForSender(SocketClient socketClient) {
        /**
         * 根据连接双方协议设置自动发送的包尾数据
         * 每次发送数据包（包括心跳包）都会在发送包内容后自动发送此包尾
         *
         * 例：socketClient.sendData(new byte[]{0x01, 0x02})的步骤为
         * 1. socketClient向远程端发送包头（如果设置了包头信息）
         * 2. socketClient向远程端发送正文数据{0x01, 0x02}
         * 3. socketClient向远程端发送包尾
         *
         * 使用{@link SocketPacketHelper.ReadStrategy.AutoReadToTrailer}必须设置此项
         * 用于分隔多条消息
         */
        socketClient.getSocketPacketHelper().setSendTrailerData(new byte[]{0x0A});

        /**
         * 设置发送超时时长
         * 在发送每个数据包时，发送每段数据的最长时间，超过后自动断开socket连接
         * 通过设置分段发送{@link SocketPacketHelper#setSendSegmentEnabled(boolean)} 可避免发送大数据包时因超时断开，
         *
         * 若无需限制发送时长可删除此二行
         */
//        socketClient.getSocketPacketHelper().setSendTimeout(30 * 1000); // 设置发送超时时长，单位毫秒
//        socketClient.getSocketPacketHelper().setSendTimeoutEnabled(true); // 设置允许使用发送超时时长，此值默认为false
    }

    private void __i__setupReadToTrailerForReceiver(SocketClient socketClient) {
        /**
         * 设置读取策略为自动读取到指定的包尾
         */
        socketClient.getSocketPacketHelper().setReadStrategy(SocketPacketHelper.ReadStrategy.AutoReadByLength);

        /**
         * 根据连接双方协议设置的包尾数据
         * 每次接收数据包（包括心跳包）都会在检测接收到与包尾数据相同的byte[]时回调一个数据包
         *
         * 例：自动接收远程端所发送的socketClient.sendData(new byte[]{0x01, 0x02})【{0x01, 0x02}为将要接收的数据】的步骤为
         * 1. socketClient接收包头（如果设置了包头信息）（接收方式为一直读取到与包头相同的byte[],即可能过滤掉包头前的多余信息）
         * 2. socketClient接收正文和包尾（接收方式为一直读取到与尾相同的byte[]）
         * 3. socketClient回调数据包
         *
         * 使用{@link SocketPacketHelper.ReadStrategy.AutoReadToTrailer}必须设置此项
         * 用于分隔多条消息
         */
        socketClient.getSocketPacketHelper().setReceiveTrailerData(new byte[]{0x0A});

        /**
         * 设置接收超时时长
         * 在指定时长内没有数据到达本地自动断开
         *
         * 若无需限制接收时长可删除此二行
         */
        socketClient.getSocketPacketHelper().setReceiveTimeout(120 * 1000); // 设置接收超时时长，单位毫秒
        socketClient.getSocketPacketHelper().setReceiveTimeoutEnabled(true); // 设置允许使用接收超时时长，此值默认为false
    }
}
