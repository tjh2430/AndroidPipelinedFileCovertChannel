/*
package com.example.filecovertchannellib.lib;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by Tim on 5/9/2014.
 */
public class ChannelUtils
{
    //public static final String PATH_ROOT = "/data/data/com.example.filepipelinecovertchannel.app/files/";
    public static final String PRIMARY_DATA_FILE = "control_d.txt";
    public static final String PRIMARY_READ_READY_FILE = "control_rr.txt";
    public static final String PRIMARY_WRITE_READY_FILE = "control_wr.txt";
    public static final long DEFAULT_SLEEP_INTERVAL = (long) 5;

    //private static final String DEFAULT_TAG = "com.example.filecovertchannellib";

    //private static final File rootDirectory = Environment.getExternalStorageDirectory();
    private static final File rootDirectory = new File("tmp");

    private static final String[][] channelFileNames = 
    {
	{"a_d.txt", "a_rr.txt", "a_wr.txt"},
	{"b_d.txt", "b_rr.txt", "b_wr.txt"},
	{"c_d.txt", "c_rr.txt", "c_wr.txt"},
	{"d_d.txt", "d_rr.txt", "d_wr.txt"},
	{"e_d.txt", "e_rr.txt", "e_wr.txt"},
	{"f_d.txt", "f_rr.txt", "f_wr.txt"},
	{"g_d.txt", "g_rr.txt", "g_wr.txt"},
	{"h_d.txt", "h_rr.txt", "h_wr.txt"},
	{"i_d.txt", "i_rr.txt", "i_wr.txt"},
	{"j_d.txt", "j_rr.txt", "j_wr.txt"},
	{"k_d.txt", "k_rr.txt", "k_wr.txt"},
	{"l_d.txt", "l_rr.txt", "l_wr.txt"},
	{"m_d.txt", "m_rr.txt", "m_wr.txt"},
	{"n_d.txt", "n_rr.txt", "n_wr.txt"},
	{"o_d.txt", "o_rr.txt", "o_wr.txt"},
	{"p_d.txt", "p_rr.txt", "p_wr.txt"}
    };

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
            
	    //FileOutputStream out = cxt.openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            //out.close();
        }
	else
	{
	    boolean success = file.setLastModified(System.currentTimeMillis());
	    if (!success)
		throw new IOException("Unable to set the last modification time for " + file);
	}
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
	    if(!syncFile.exists())
	    {
		return -1;
	    }

	    if(syncFile.lastModified() > lastUpdateTime)
            {
                updatedTime = syncFile.lastModified();
                break;
            }

            Thread.sleep(sleepInterval);
        }

        return updatedTime;
    }

    /* TODO: Uncomment or remove
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
        output("ChannelUtils", "Made read ready file with path " + rrF.getAbsolutePath());
        return rrF;

        // TODO: Uncomment
        //return new File(PATH_ROOT, PRIMARY_READ_READY_FILE);
    }

    public static File getPrimaryWriteReadyFile()
    {
        return new File(rootDirectory, PRIMARY_WRITE_READY_FILE);
        //return new File(PATH_ROOT, PRIMARY_WRITE_READY_FILE);
    }
    */

    public static List<Channel> getPipes(Channel.CHANNEL_MODE mode)
	throws IOException
    {
	return getPipes(DEFAULT_SLEEP_INTERVAL, mode);
    }

    public static List<Channel> getPipes(long sleepInterval, Channel.CHANNEL_MODE mode)
	throws IOException
    {
	List<Channel> pipes = new LinkedList<Channel>();

	for(int i = 0; i < channelFileNames.length; i++)
	{
	    File[] channelFiles = getFiles(channelFileNames[i]);
	    pipes.add(new Channel(channelFiles[0], // data file
				  channelFiles[1], // read-ready file
				  channelFiles[2], // write-ready file
				  sleepInterval,
				  mode));
	}

	return pipes;
    }

    public static File[] getFiles(String[] fileNames)
    {
	File[] files = new File[fileNames.length];
	for(int i = 0; i < fileNames.length; i++)
	{
	    files[i] = new File(rootDirectory, fileNames[i]);
	}

	return files;
    }

    public static int getMaxNumPipes()
    {
	return channelFileNames.length;
    }

    public static boolean isValidNumberOfPipes(int numPipes)
    {
	return (numPipes > 0 && numPipes <= getMaxNumPipes());
    }

    public static void output(String msg)
    {
	//output(DEFAULT_TAG, msg);
	System.out.println(msg);
    }

    public static void output(String tag, String msg)
    {
	// TODO: Uncomment if in Android
	// Log.d(tag, msg);
	System.out.println(tag + ": " + msg);
    }
}
