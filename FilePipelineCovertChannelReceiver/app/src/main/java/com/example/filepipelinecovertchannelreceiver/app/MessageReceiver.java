package com.example.filepipelinecovertchannelreceiver.app;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.filecovertchannellib.lib.FileChannel;
import com.example.filecovertchannellib.lib.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Daniyal on 5/17/2014.
 */
public class MessageReceiver extends Service
{
    private static final String TAG = "com.example.filepipelinecovertchannelreceiver.app.MessageReceiver";

    private File dataFile;
    private File readReadyFile;
    private File writeReadyFile;
    private long lastUpdateTime;
    private StringBuilder receivedMessage;
    private FileChannel channel;

    // This is the object that receives interactions from clients.
    private final IBinder mBinder = new MessageBinder();

    @Override
    public void onCreate()
    {
        super.onCreate();
        dataFile = FileUtils.getPrimaryDataFile();
        readReadyFile = FileUtils.getPrimaryReadReadyFile();
        writeReadyFile = FileUtils.getPrimaryWriteReadyFile();
        lastUpdateTime = 0;
        receivedMessage = new StringBuilder();

        // Initializing channel
        try {
            channel = new FileChannel(dataFile, readReadyFile, writeReadyFile, this, FileUtils.DEFAULT_SLEEP_INTERVAL, FileChannel.CHANNEL_MODE.RECEIVER);
            channel.openChannel();
        } catch (IOException e) {
            Log.e(TAG, "Unable to instantiate primary channel");
        }

        // Set writeReady flag
        try {
            FileUtils.touch(writeReadyFile, this);
        } catch (IOException e) {
            Log.e(TAG, "Unable to touch writeReadyFile");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    Log.d(TAG, "Receiving message in service...");
                    String message = channel.receiveMessage();
                    messageReceived(message);
                    Log.d(TAG, "Message " + message + " received");
                    /*
                    try {
                        lastUpdateTime = FileUtils.waitOn(readReadyFile, lastUpdateTime, FileUtils.DEFAULT_SLEEP_INTERVAL);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Unable to block for readReadyFile");
                    }
                    Log.e(TAG, "Unable to block for readReady flag");

                    try {
                        FileUtils.touch(writeReadyFile, MessageReceiver.this);
                    } catch (IOException e) {
                        Log.e(TAG, "Unable to touch writeReadyFile");
                    }
                    */
                }
            }
        }).start();

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    public synchronized void messageReceived(String message)
    {
        receivedMessage.append(message);
    }

    public String getMessage()
    {
        return receivedMessage.toString();
    }

    public class MessageBinder extends Binder
    {
        public MessageReceiver getService()
        {
            return MessageReceiver.this;
        }
    }
}
