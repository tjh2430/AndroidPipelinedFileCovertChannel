package com.example.filepipelinecovertchannelreceiver.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.example.filecovertchannellib.lib.FileChannel;
import com.example.filecovertchannellib.lib.FileUtils;
import com.example.filepipelinecovertchannelreceiver.app.MessageReceiver.MessageBinder;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity implements ServiceConnection
{
    private static final String TAG = "com.example.filepipelinecovertchannelreceiver.app.MainActivity";

    private TextView outputTextView;
    private MessageReceiver receiverService;
    private StringBuilder message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = new StringBuilder();
        receiverService = null;

        Intent receiverServiceIntent = new Intent("com.example.filepipelinecovertchannelreceiver.app.MessageReceiver");
        bindService(receiverServiceIntent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected  void onResume()
    {
        super.onResume();

        if(receiverService != null)
        {
            updateMessageDisplay();
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder)
    {
        MessageBinder messageBinder = (MessageBinder) iBinder;
        receiverService = messageBinder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName)
    {
        receiverService = null;
    }

    public void updateMessageDisplay()
    {
        String receivedMessage = receiverService.getMessage();

        if(receivedMessage != null)
        {
            message.append(receivedMessage);
            outputTextView.setText(message.toString());
        }
        else
        {
            Log.d(TAG, "Null message received");
        }
    }
}
