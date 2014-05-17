package com.example.filepipelinecovertchannelreceiver.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.filecovertchannellib.lib.FileChannel;
import com.example.filecovertchannellib.lib.FileUtils;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity
{
    private static final String TAG = "com.example.filepipelinecovertchannelreceiver.app.MainActivity";

    private TextView outputTextView;
    private FileChannel channel;
    private File dataFile;
    private File readReadyFile;
    private File writeReadyFile;
    private long lastUpdateTime;
    private String receiveMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataFile = FileUtils.getPrimaryDataFile();
        readReadyFile = FileUtils.getPrimaryReadReadyFile();
        writeReadyFile = FileUtils.getPrimaryWriteReadyFile();
        lastUpdateTime = 0;

        outputTextView = (TextView) findViewById(R.id.received_message);
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
    protected  void onResume()
    {
        // TODO: Put channel listening stuff and output here
        outputTextView.setText(receiveMessage);
    }
}
