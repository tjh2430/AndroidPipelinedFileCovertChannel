package com.example.filepipelinecovertchannel.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.filecovertchannellib.lib.FileChannel;
import com.example.filecovertchannellib.lib.FileUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity
{
    private static final String TAG = "com.example.filepipelinecovertchannel.app.MainActivity";

    private EditText messageEntry;
    private TextView outputDisplay;
    private FileChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageEntry = (EditText) findViewById(R.id.messageEntry);
        outputDisplay = (TextView) findViewById(R.id.statusMessageDisplay);

        File dataFile = FileUtils.getPrimaryDataFile();
        File readReadyFile = FileUtils.getPrimaryReadReadyFile();
        File writeReadyFile = FileUtils.getPrimaryWriteReadyFile();

        try
        {
            channel = new FileChannel(dataFile, readReadyFile, writeReadyFile, this, FileUtils.DEFAULT_SLEEP_INTERVAL, FileChannel.CHANNEL_MODE.SENDER);
            channel.openChannel();
        }
        catch(IOException e)
        {
            Log.e(TAG, "Unable to instantiate primary channel");
        }
    }

    public void sendMessageClick(View v)
    {
        String message = messageEntry.getText().toString();

        if(message == null || message.length() == 0)
        {
            outputDisplay.setText("Cannot send message; message length is zero.");
        }
        else
        {
            channel.sendMessage(message);
            outputDisplay.setText("Message '" + message + "' was sent");
        }
    }
}
