package com.example.filepipelinecovertchannelreceiver.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.filecovertchannellib.lib.FileChannel;


public class MainActivity extends Activity
{
    private TextView outputTextView;
    private FileChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTextView = (TextView) findViewById(R.id.received_message);
        // TODO: Initialize FileChannel here
    }

    @Override
    protected  void onResume()
    {
        // TODO: Put channel listening stuff and output here
    }
}
