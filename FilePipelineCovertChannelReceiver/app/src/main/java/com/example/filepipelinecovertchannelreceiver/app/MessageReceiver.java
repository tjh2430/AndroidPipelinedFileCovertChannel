package com.example.filepipelinecovertchannelreceiver.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.filecovertchannellib.lib.FileUtils;

import java.io.IOException;

/**
 * Created by Daniyal on 5/17/2014.
 */
public class MessageReceiver extends Service{
    @Override
    public void OnCreate(){

    }

    @Override
    protected int onStartCommand(Intent intent, int flags, int startId) {

        while(true) {
            try {
                lastUpdateTime = FileUtils.waitOn(readReadyFile, lastUpdateTime, FileUtils.DEFAULT_SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                Log.e(TAG, "Unable to block for readReadyFile");
            }
            Log.e(TAG, "Unable to block for readReady flag");

            try {
                FileUtils.touch(writeReadyFile, this);
            } catch (IOException e) {
                Log.e(TAG, "Unable to touch writeReadyFile");
            }
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
