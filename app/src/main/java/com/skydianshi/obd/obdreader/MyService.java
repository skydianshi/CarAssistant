package com.skydianshi.obd.obdreader;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;


import com.skydianshi.obd.obd.BusInitObdCommand;
import com.skydianshi.obd.obd.EngineCoolantTemperatureObdCommand;
import com.skydianshi.obd.obd.EngineRPMObdCommand;
import com.skydianshi.obd.obd.IntakeManifoldPressureObdCommand;
import com.skydianshi.obd.obd.MassAirFlowObdCommand;
import com.skydianshi.obd.obd.SpeedObdCommand;
import com.skydianshi.obd.obd.ThrottlePositionObdCommand;
import com.skydianshi.obd.obd.protocol.EchoOffObdCommand;
import com.skydianshi.obd.obd.protocol.LineFeedOffObdCommand;
import com.skydianshi.obd.obd.protocol.ObdProtocols;
import com.skydianshi.obd.obd.protocol.ObdResetCommand;
import com.skydianshi.obd.obd.protocol.SelectProtocolObdCommand;
import com.skydianshi.obd.obd.protocol.TimeoutObdCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MyService extends Service {
    private MyBinder binder = new MyBinder();
    String address;
    private BlockingQueue<ObdCommand> _queue = new LinkedBlockingQueue<ObdCommand>();
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    BluetoothDevice _device = null;     //蓝牙设备
    BluetoothSocket _socket = null;      //蓝牙通信socket
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();  //获取本地蓝牙适配器，即蓝牙设备
    InputStream in;
    OutputStream os;

    IPostListener _callback;

    public  class MyBinder extends Binder {
        public void setListener(IPostListener callback){//这边定义调用接口的方法，同时在下面读取数据的时候调用这个方法则实现了实时更新UI界面的效果
            _callback = callback;
            System.out.println("binded");
        }//将service里面的接口和activity里面的接口连接起来，从而可以调用activity中已经实现的方法
        public void addJob(){
            addJobToQueue();
        }
    }

    public void addJobToQueue(){
        try {
            _queue.put(new SpeedObdCommand());
            _queue.put(new EngineRPMObdCommand());
            _queue.put(new MassAirFlowObdCommand());
            _queue.put(new IntakeManifoldPressureObdCommand());
            _queue.put(new EngineCoolantTemperatureObdCommand());
            _queue.put(new ThrottlePositionObdCommand());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void initialJob(){//EML327初始化工作
        try {
            _queue.put(new ObdResetCommand());
            _queue.put(new EchoOffObdCommand());
            _queue.put(new LineFeedOffObdCommand());
		/*
		 * * Will send second-time based on tests. * * TODO this can be done w/o
		 * having to queue jobs by just issuing * command.run(),
		 * command.getResult() and validate the result.
		 */
            _queue.put(new TimeoutObdCommand(25)); // 等待响应250ms
            // For now set protocol to AUTO
            _queue.put(new SelectProtocolObdCommand(ObdProtocols.AUTO));
            _queue.put(new BusInitObdCommand());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is Binded");
        //从activity中得到传过来的蓝牙与OBD建立的通道的地址，方便OnstartCommand里面通过地址获得蓝牙通道，方便数据传输
        Bundle bundle = intent.getExtras();
        address = bundle.getString("address");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service is created");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("-----onStartCommand");
       startService();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startService(){
        getSocket();
        ReadThread.start();
    }

    Thread ReadThread=new Thread(){

        public void run(){
            //接收线程
                try{
                    while(true){
                        ObdCommand command = null;
                        try {
                            command = (ObdCommand)_queue.take();//从队列中取出要更新的任务，如果队伍中没有任务，就一直等在这边等待任务
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try{
                            command.sendCommand(os);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        command.readResult(in);
                        if(_callback!=null){//提醒UI线程更新
                            _callback.stateUpdate(command,command.getFormattedResult().toString());
                        }
                    }
                }catch(IOException e){
                    System.out.println("发送数据或者读取数据失败");
                }

        }
    };


    //activity中蓝牙与OBD已经建立了通道，这里通过地址获得通道，从而在service中进行数据传输
    public void getSocket(){
        _device = _bluetooth.getRemoteDevice("00:1D:A5:00:01:47");
        try {
            _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
            _socket.connect();
            System.out.println("连接成功");
        }catch (IOException e) {
            System.out.println("连接失败");
        }
        try {
            in = _socket.getInputStream();
            os = _socket.getOutputStream();
        }catch (IOException e){
            System.out.println("-------获取通道失败");
        }
       //初始化EML327
        initialJob();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }
}
