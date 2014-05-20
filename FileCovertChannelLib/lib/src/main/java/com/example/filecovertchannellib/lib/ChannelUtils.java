package com.example.filecovertchannellib.lib;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Tim on 5/9/2014.
 */
public class ChannelUtils
{
    //public static final String PATH_ROOT = "/data/data/com.example.filepipelinecovertchannel.app/files/";
    public static final String PRIMARY_DATA_FILE = "control_d.txt";
    public static final String PRIMARY_READ_READY_FILE = "control_rr.txt";
    public static final String PRIMARY_WRITE_READY_FILE = "control_wr.txt";
    public static final int DEFAULT_SLEEP_INTERVAL = 5;

    private static final File rootDirectory = Environment.getExternalStorageDirectory();

    /**
     * Updates the timestamp of the given file to the current time.
     * Taken from http://www.intransitione.com/blog/touch-a-file-on-android/
     */
    public static void touch(File file, Context cxt) throws IOException {
        if(!file.exists())
        {
            File parent = file.getParentFile();
            if(parent != null)
                if(!parent.exists())
                    if(!parent.mkdirs())
                        throw new IOException("Cannot create parent directories for file: " + file);

            file.createNewFile();
            FileOutputStream out = cxt.openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            out.close();
        }

        boolean success = file.setLastModified(System.currentTimeMillis());
        if (!success)
            throw new IOException("Unable to set the last modification time for " + file);
    }

    /**
     * Waits until the given syncFile is updated ("touched") by another process. The caller
     * will sleep for sleepInterval milliseconds in between each check of the file.
     *
     * @param syncFile          The file being monitored
     * @param lastUpdateTime    The time of the last known update to the file
     * @param sleepInterval     How long to sleep between checks of the file's last update time
     *
     * @return                  An updated last-modified-time value
     *
     * @throws InterruptedException If the calling thread is interrupted while asleep.
     */
    public static long waitOn(File syncFile, long lastUpdateTime, long sleepInterval)
        throws InterruptedException
    {
        long updatedTime;
        while(true)
        {
            if(syncFile.lastModified() > lastUpdateTime)
            {
                updatedTime = syncFile.lastModified();
                break;
            }

            Thread.sleep(sleepInterval);
        }

        return updatedTime;
    }

    public static File getPrimaryDataFile()
    {
        return new File(rootDirectory, PRIMARY_DATA_FILE);
        //return new File(PATH_ROOT, PRIMARY_DATA_FILE);
    }

    public static File getPrimaryReadReadyFile()
    {
        // TODO: Remove
        File rrF = new File(rootDirectory, PRIMARY_READ_READY_FILE);
        //File rrF = new File(PATH_ROOT, PRIMARY_READ_READY_FILE);
        Log.d("ChannelUtils", "Made read ready file with path " + rrF.getAbsolutePath());
        return rrF;

        // TODO: Uncomment
        //return new File(PATH_ROOT, PRIMARY_READ_READY_FILE);
    }

    public static File getPrimaryWriteReadyFile()
    {
        return new File(rootDirectory, PRIMARY_WRITE_READY_FILE);
        //return new File(PATH_ROOT, PRIMARY_WRITE_READY_FILE);
    }
}
