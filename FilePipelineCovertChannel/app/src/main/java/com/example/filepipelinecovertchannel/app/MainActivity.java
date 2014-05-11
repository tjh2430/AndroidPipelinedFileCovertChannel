package com.example.filepipelinecovertchannel.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.filecovertchannellib.lib.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity
{
    private EditText messageEntry;
    private TextView outputDisplay;
    private File writeready = new File("writeready");
    private FileOutputStream carrier;
    private FileOutputStream readready;

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
            //break string into chars
            for(int i = 0; i < message.length(); i++) {
                char c = message.charAt(i);
                //check for writeready flag
                if( writeready.exists() ) {
                    //encode into carrier file
                    carrier = openFileOutput("carrier", Context.MODE_WORLD_READABLE);
                    carrier.write(c);
                    carrier.close();
                }
                readready = openFileOutput("readready", Context.MODE_WORLD_READABLE);
                readready.write(1);
                readready.close();
            }

            outputDisplay.setText("Message '" + message + "' was sent");
        }
    }
}
