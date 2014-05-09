package com.example.filecovertchannellib.lib;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tim on 5/9/2014.
 */
public class FileUtils
{
    /**
     * Updates the timestamp of the given file to the current time.
     * Taken from http://www.intransitione.com/blog/touch-a-file-on-android/
     */
    public static void touch(File file) throws IOException {
        if(!file.exists())
        {
            File parent = file.getParentFile();
            if(parent != null)
                if(!parent.exists())
                    if(!parent.mkdirs())
                        throw new IOException("Cannot create parent directories for file: " + file);

            file.createNewFile();
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
}
