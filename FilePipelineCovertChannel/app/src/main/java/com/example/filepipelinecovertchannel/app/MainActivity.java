package com.example.filepipelinecovertchannel.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.filecovertchannellib.lib.FileUtils;

public class MainActivity extends Activity
{
    private EditText messageEntry;
    private TextView outputDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageEntry = (EditText) findViewById(R.id.messageEntry);
        outputDisplay = (TextView) findViewById(R.id.statusMessageDisplay);
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
            outputDisplay.setText("Message '" + message + "' was sent");
        }
    }
}
